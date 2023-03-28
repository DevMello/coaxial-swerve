package tk.devmello.robot.stages.stage;

import tk.devmello.robot.hardware.RobotPart;
import tk.devmello.mellolib.util.iter.FinalInteger;
import tk.devmello.mellolib.util.Iterator;

import java.util.ArrayList;
import java.util.Arrays;

public class Stage {

    /**
     * Stage has an arraylist of stagecomponents
     */
    private final ArrayList<StageComponent> components = new ArrayList<>();
    /**
     * Has the stage started?
     */
    private volatile boolean hasStarted = false;
    /**
     * Is this stage a pause?
     */
    private volatile boolean isPause = false;

    /**
     * Create a stage using stage components
     * @param stageComponents
     */
    public Stage(StageComponent...stageComponents){
        components.addAll(Arrays.asList(stageComponents)); addDefaults();
    }

    public Stage(ArrayList<StageComponent> stageComponents){
        components.addAll(stageComponents); addDefaults();
    }

    private Stage(boolean defaults, boolean other){}

    /**
     * Create a stage that is a pause
     * @param isPause
     */
    public Stage(boolean isPause){
        this.isPause = isPause;
    }

    /**
     * Has the stage started
     * @return hasStarted
     */
    public boolean hasNotStartedYet(){
        return !hasStarted;
    }

    /**
     * Start the stage
     */
    public void start(){
        Iterator.forAll(components, StageComponent::start);
        hasStarted = true;
    }

    /**
     * Loop
     */
    public void loop(){
        Iterator.forAll(components, StageComponent::loop);
    }

    /**
     * Should the stage stop
     * @return should stop
     */
    public boolean shouldStop(){
        return Iterator.forAllConditionOR(components, StageComponent::shouldStop);
    }

    /**
     * Run on stop
     */
    public void runOnStop(){
        Iterator.forAll(components, StageComponent::runOnStop);
        hasStarted = false;
    }

    /**
     * Is this stage a pause
     * @return isPause
     */
    public boolean isPause(){
        return isPause;
    }


    /**
     * Add default components if they are not added
     */
    private void addDefaults(){
        if(Iterator.forAllCount(components, comp -> comp instanceof Exit) == 0){
            components.add(RobotPart.exitAlways());
        }
    }

    public Stage combine(Stage stage){ Iterator.forAll(stage.components, components::add); return this; }
    public Stage combine(StageComponent... stageComponents){ components.addAll(Arrays.asList(stageComponents)); return this; }

    /**
     * The new stage "attaches" to the old one. NOTE: This is different than combine, because the exit condition of the new stage overlaps
     * [ --- old stage --- ]
     * [ ------ new stage ------ ]
     * OR
     *  [ ------ old stage ------ ]
     *  [ --- new stage --- ]
     * @param stage
     * @return attached stage
     */
    public Stage attach(Stage stage){
        final ArrayList<StageComponent> oldComponents = new ArrayList<>(this.components);
        final ArrayList<StageComponent> newComponents = new ArrayList<>(stage.components);
        FinalInteger exitCode = new FinalInteger();
        return new Stage(false, false){
            @Override
            public void start() { Iterator.forAll(oldComponents, StageComponent::start); Iterator.forAll(newComponents, StageComponent::start); super.hasStarted = true; }
            @Override
            public void loop() { switch (exitCode.get()) {
                case 0: Iterator.forAll(oldComponents, StageComponent::loop); Iterator.forAll(newComponents, StageComponent::loop); boolean oldStop = Iterator.forAllConditionOR(oldComponents, StageComponent::shouldStop); boolean newStop = Iterator.forAllConditionOR(newComponents, StageComponent::shouldStop); if (oldStop && newStop) { Iterator.forAll(oldComponents, StageComponent::runOnStop); Iterator.forAll(newComponents, StageComponent::runOnStop); exitCode.set(3); } else if (oldStop) { Iterator.forAll(oldComponents, StageComponent::runOnStop); exitCode.set(1); } else if (newStop) { Iterator.forAll(newComponents, StageComponent::runOnStop); exitCode.set(2); } break;
                case 1: Iterator.forAll(newComponents, StageComponent::loop); if (Iterator.forAllConditionOR(newComponents, StageComponent::shouldStop)) { Iterator.forAll(newComponents, StageComponent::runOnStop); exitCode.set(3); } break;
                case 2: Iterator.forAll(oldComponents, StageComponent::loop); if (Iterator.forAllConditionOR(oldComponents, StageComponent::shouldStop)) { Iterator.forAll(oldComponents, StageComponent::runOnStop); exitCode.set(3); } break;
            }}
            @Override public boolean shouldStop(){ return exitCode.equals(3); }
            @Override public void runOnStop() { switch (exitCode.get()){
                case 0: Iterator.forAll(oldComponents, StageComponent::runOnStop); Iterator.forAll(newComponents, StageComponent::runOnStop); break;
                case 1: Iterator.forAll(newComponents, StageComponent::runOnStop); break;
                case 2: Iterator.forAll(oldComponents, StageComponent::runOnStop); break;
            } super.hasStarted = false; exitCode.set(0); }
        };
    }
}

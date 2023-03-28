package tk.devmello.robot.stages;

import tk.devmello.mellolib.codeseg.CodeSeg;
import tk.devmello.mellolib.codeseg.ParameterCodeSeg;
import tk.devmello.mellolib.codeseg.ReturnCodeSeg;
import tk.devmello.mellolib.util.Timer;
import tk.devmello.robot.stages.stage.*;

import java.util.ArrayList;

public class StageBuilder {
    /**
     * Exit based on time
     * @param s
     * @return exit
     */
    //TODO: Fix this
    public static Exit exitTime(double s){return new Exit(() -> 0 > s);}

    /**
     * Exit always
     * @return exit
     */
    public static Exit exitAlways(){return new Exit(() -> true);}

    /**
     * Exit never
     * @return exit
     */
    public static Exit exitNever(){return new Exit(() -> false);}


    /**
     * Pause for some amount of time
     * @param time
     * @return
     */
    public static Stage pause(double time){ return new Stage(exitTime(time));}

    public static Stage empty(){return new Stage(exitAlways());}

    public static Stage emptyIfNot(boolean condition, Stage alternative){ return condition ? alternative : empty();}

    public static Stage condition(boolean condition, Stage one, Stage two){return condition ? one : two;}

    /**
     * Stop the part
     * @return stop
     */
    protected Stop stop(){ return new Stop(() -> {}); }


    /**
     * Use this robot part
     * NOTE: This must be called before the robot part can be used in a stage
     * @return initial
     */
    public Initial usePart(){return new Initial(() -> {});}

    /**
     * Return the robot part to the main user
     * NOTE: This must be called after the robot part is use in a stage
     * @return stop
     */
    public Stop returnPart(){return new Stop(() -> {});}


    /**
     * Maintain the current state of the part (for positional parts, hold position)
     */
    protected void maintain(){}


    /**
     * Methods to override and use to create stages
     */

    protected void move(double vx, double vy, double va){}
    protected Main main(double vx, double vy, double va){ return new Main(() -> move(vx, vy, va)); }
    protected Stage moveTime(double fp, double sp, double tp, double t){ return new Stage(usePart(), main(fp, sp, tp), exitTime(t), stop(), returnPart()); }
    //TODO: Fix this
    //protected Stage moveTime(double fp, double sp, double tp, ReturnCodeSeg<Double> t){ return new Stage(usePart(), main(fp, sp, tp), new Exit(() -> bot.rfsHandler.getTimer().seconds() > t.run()), stop(), returnPart()); }

    protected void move(double p){}
    protected Main main(double p){ return new Main(() -> move(p)); }
    protected Stage moveTime(double p, double t){ return new Stage(usePart(), main(p), exitTime(t), stop(), returnPart()); }
    //TODO: Fix this
    //protected Stage moveTime(double p, ReturnCodeSeg<Double> t){ final Double[] val = {0.0}; return new Stage(usePart(), new Initial(() -> val[0] = t.run()), main(p), new Exit(() -> { synchronized (val){ return bot.rfsHandler.getTimer().seconds() > val[0]; }}), stop(), returnPart()); }
    protected Stage moveNow(double p){ return new Stage(usePart(), main(p), exitAlways(), stop(), returnPart()); }


    // TODO Problem with using exitAlways
    protected final Stage moveCustomExit(double fp, double sp, double tp, Exit exit){ return new Stage(usePart(), main(fp, sp, tp), exit, stop(), returnPart()); }
    protected final Stage customExit(double p, Exit exit){ return new Stage(usePart(), main(p), exit, stop(), returnPart()); }
    protected final Stage customExit(double p, ReturnCodeSeg<Boolean> exit){ return new Stage(usePart(), main(p), new Exit(exit), stop(), returnPart()); }
    protected final Stage customTime(CodeSeg m, double t){ return new Stage(usePart(), new Main(m), t != 0.0 ? exitTime(t) : exitTime(0.05), stop(), returnPart()); }
    protected final Stage customTime(Main m, double t){ return new Stage(usePart(), m, t != 0.0 ? exitTime(t) : exitTime(0.05), stop(), returnPart()); }
//    protected final Stage customTimeAfter(CodeSeg m, double t){
//        Boolean[] exit = {false};
//        return new Stage(usePart(), new Initial(() -> {exit[0] = false;}), new Main(()-> {
//            if(bot.rfsHandler.getTimer().seconds() > (t != 0 ? t : 0.05)){
//                exit[0] = true;
//                m.run();
//            }
//        }), new Exit(() -> exit[0]), returnPart()); }
    //TODO: Fix this
//    protected final Stage customTimeAfter(CodeSeg m, double t){
//        Boolean[] exit = {false};
//        return new Stage(usePart(), new Initial(() -> {exit[0] = false;}), new Main(()-> {
//            if(t < 0.05 || bot.rfsHandler.getTimer().seconds() > t){
//                exit[0] = true;
//                m.run();
//            }
//        }),  t < 0.05 ? RobotPart.exitAlways() : new Exit(() -> exit[0]) , returnPart()); }

    protected void setTarget(double target){}
    protected boolean exitTarget(){ return true; }
    protected void stopTarget(){}


    protected final Stage customTime(ParameterCodeSeg<Double> code, Exit exit){ final Timer timer = new Timer(); return new Stage(usePart(),  new Initial(timer::reset), new Main(() -> {double time = timer.seconds(); code.run(time);}), exit, returnPart());}
    protected final Stage customTime(StageBuilderTime stageBuilderTime){ return stageBuilderTime.getStage(); }

    protected static class StageBuilderTime {
        private final ArrayList<Double> times = new ArrayList<>();
        private final ArrayList<CodeSeg> subStages = new ArrayList<>();
        private final StageBuilder stageBuilder;
        private int subStageNumber;

        public StageBuilderTime(StageBuilder stageBuilder){ times.clear(); subStages.clear(); times.add(0.0); this.stageBuilder = stageBuilder; subStageNumber = 0; }

        public StageBuilderTime addSubStage(double time, CodeSeg code){ times.add(time + times.get(times.size()-1)); subStages.add(code); return this; }

        public Stage getStage(){
            return stageBuilder.customTime(time -> {
                        if(subStageNumber < subStages.size()){ subStages.get(subStageNumber).run(); }
                        if(time >= times.get(subStageNumber+1)){ subStageNumber++; }
                    }, new Exit(() -> subStageNumber >= subStages.size())
            ).combine(new Stop(() -> subStageNumber = 0));
        }
    }
}


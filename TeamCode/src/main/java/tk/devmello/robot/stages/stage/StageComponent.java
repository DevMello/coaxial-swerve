package tk.devmello.robot.stages.stage;

public class StageComponent {
    /**
     * Defines a part of a stage
     */
    protected void start(){}
    protected void loop(){}
    protected boolean shouldStop(){return false;}
    protected void runOnStop(){}
}

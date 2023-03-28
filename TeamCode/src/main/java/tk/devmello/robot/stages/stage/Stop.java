package tk.devmello.robot.stages.stage;

import tk.devmello.mellolib.codeseg.CodeSeg;

public class Stop extends StageComponent{
    /**
     * Code that runs on stop or end of the stage
     */
    private final CodeSeg stop;
    public Stop(CodeSeg runOnStop){
        stop = runOnStop;
    }
    @Override
    public void runOnStop(){
        stop.run();
    }
}

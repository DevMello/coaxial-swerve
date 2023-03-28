package tk.devmello.robot.stages.stage;

import tk.devmello.mellolib.codeseg.ReturnCodeSeg;

public class Exit extends StageComponent{
    /**
     * Tells the stage to exit
     */
    private final ReturnCodeSeg<Boolean> shouldEnd;

    public Exit(ReturnCodeSeg<Boolean> endCode){
        shouldEnd = endCode;
    }
    @Override
    public boolean shouldStop(){
        return shouldEnd.run();
    }
}

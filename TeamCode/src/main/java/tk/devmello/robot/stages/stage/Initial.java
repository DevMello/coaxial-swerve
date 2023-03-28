package tk.devmello.robot.stages.stage;

import tk.devmello.mellolib.codeseg.CodeSeg;

public class Initial extends StageComponent{
    /**
     * Initial runs once in the start of a stage
     */
    private final CodeSeg start;
    public Initial(CodeSeg startCode){
        start = startCode;
    }
    @Override
    public void start(){
        start.run();
    }
}

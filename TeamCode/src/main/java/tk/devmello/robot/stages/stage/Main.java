package tk.devmello.robot.stages.stage;

import tk.devmello.mellolib.codeseg.CodeSeg;

public class Main extends StageComponent{
    /**
     * Main runs over and over in a stage after main
     */
    private final CodeSeg loop;
    public Main(CodeSeg loopCode){
        loop = loopCode;
    }
    @Override
    public void loop(){
        loop.run();
    }
}

package tk.devmello.robot.util;

import tk.devmello.mellolib.codeseg.CodeSeg;
import tk.devmello.mellolib.util.Timer;
import tk.devmello.robot.stages.stage.Exit;

public class BackgroundTask {
    /**
     * Class to represent background task
     */

    /**
     * Has the background task been started?
     */
    private volatile boolean hasBeenStarted = false;

    /**
     * Timer object
     */
    private final Timer timer = new Timer();

    /**
     * Code to run
     */
    private final CodeSeg task;

    /**
     * Exit condition
     */

    private final Exit exit;

    /**
     * Create background task with code
     * @param task
     */
    public BackgroundTask(CodeSeg task) {
        this.task = task;
        exit = new Exit(() -> false);
    }

    /**
     * Create background task with custom exit condition
     * @param task
     * @param exit
     */
    public BackgroundTask(CodeSeg task, Exit exit) {
        this.task = task;
        this.exit = exit;
    }

    /**
     * Run the background task
     */
    public void run(){
        if(!hasBeenStarted){
            timer.reset();
            hasBeenStarted = true;
        }
        task.run();
    }

}

package tk.devmello.robot.bot;

import tk.devmello.mellolib.codeseg.ExceptionCodeSeg;
import tk.devmello.mellolib.threading.util.ThreadStatus;
import tk.devmello.mellolib.util.Iterator;
import tk.devmello.mellolib.util.Timer;
import tk.devmello.robot.stages.stage.Stage;
import tk.devmello.robot.util.User;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class RobotFunctions {
    /**
     * List of all robot functions currently in the queue (LinkedList is a FIFO Queue)
     *
     */
    private final Queue<Stage> rfsQueue = new LinkedList<>();
    /**
     * Timer for robotfunctions, resets after every stage is run
     */
    private final Timer timer = new Timer();
    /**
     * Define the updateCode codeseg to contain the code that will run in the Thread
     */
    public ExceptionCodeSeg<RuntimeException> updateCode = () -> {
        /**
         * Check if the robot has access to move for robotfunctions user or ROFU
         */
        General.bot.checkAccess(User.ROFU);
        /**
         * If the robotfunctions queue is not empty
         */
        synchronized (rfsQueue) {
            if (!rfsQueue.isEmpty()) {
                /**
                 * Get the oldest stage
                 */
                Stage s = rfsQueue.peek();
                /**
                 * If the stage has not started start it,
                 * Then run the loop code
                 */
                if (Objects.requireNonNull(s).hasNotStartedYet()) {
                    s.start();
                    synchronized (timer) {
                        timer.reset();
                    }
                }
                s.loop();
                /**
                 * If the stage should stop, then run on stop code, remove the stage, and reset the timer
                 * Otherwise, set the thread to Status.IDLE to prevent unnecessary lag
                 */
                if (s.shouldStop() && !s.isPause()) {
                    s.runOnStop();
                    rfsQueue.poll();
                } else if (s.isPause()) {
                    RobotFramework.robotFunctionsThread.setStatus(ThreadStatus.IDLE);
                }
            } else {
                RobotFramework.robotFunctionsThread.setStatus(ThreadStatus.IDLE);
            }
        }
    };

    /**
     * Resume the robotfunctions,
     * if the queue is not empty and the oldest stage is a pause then delete it and start the thread again
     * (by setting the status to active)
     */
    public void resume() {
        if (!rfsQueue.isEmpty() && rfsQueue.peek().isPause()) {
            rfsQueue.poll();
            RobotFramework.robotFunctionsThread.setStatus(ThreadStatus.ACTIVE);
        }
    }

    /**
     * Initialize the update code in the thread,
     * Make the first stage a pause so the thread doesn't start updating until resume is called
     */
    public void init(){
        addToQueue(new Stage(true));
        RobotFramework.robotFunctionsThread.setExecutionCode(updateCode);
    }

    /**
     * Add the automodule by resetting the time and starting the thread
     * Add all of the stages in the automodule to the queue
     * @param autoModule
     */



    /**
     * Add a specific stage to the the queue
     * @param s
     * @link addAutoModule
     */
    public final void addToQueue(Stage s) {
        synchronized (rfsQueue) {
            if (rfsQueue.isEmpty()) { RobotFramework.robotFunctionsThread.setStatus(ThreadStatus.ACTIVE); }
            rfsQueue.add(s);
        }
    }

    /**
     * Pause the robotfunction queue in the current state after the stage has ended
     */
    public final void pauseNow() {
        Queue<Stage> newStages = new LinkedList<>();
        if (!rfsQueue.isEmpty()) {
            newStages.add(rfsQueue.poll());
        }
        newStages.add(new Stage(true));
        while (!rfsQueue.isEmpty()) {
            newStages.add(rfsQueue.poll());
        }
        Iterator.forAll(newStages, rfsQueue::add);
    }

    /**
     * Empty the queue and reset the timer
     */
    public final void emptyQueue(){
        synchronized (rfsQueue) {
            if (!rfsQueue.isEmpty()) {
                Stage s = rfsQueue.peek();
                rfsQueue.clear();
                assert s != null;
                s.runOnStop();
            }
        }
    }


    public Timer getTimer(){ return timer; }
    public Queue<Stage> getRfsQueue(){ return rfsQueue; }
}

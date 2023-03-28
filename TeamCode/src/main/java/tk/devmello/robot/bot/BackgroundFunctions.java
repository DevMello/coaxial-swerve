package tk.devmello.robot.bot;

import tk.devmello.mellolib.codeseg.ExceptionCodeSeg;
import tk.devmello.mellolib.threading.util.ThreadStatus;
import tk.devmello.mellolib.util.Iterator;
import tk.devmello.robot.util.BackgroundTask;
import tk.devmello.robot.util.User;

import java.util.ArrayList;

public class BackgroundFunctions {
    /**
     * Class to run parts of the robot in the background (stabilize lift, read encoder values, etc)
     */


    /**
     * List of tasks to run
     */
    public final ArrayList<BackgroundTask> tasks;

    /**
     * Remove all previous tasks in constructor
     */
    public BackgroundFunctions(){ tasks = new ArrayList<>(); }

    /**
     * Initialize the background thread with looping through all tasks
     */
    public void init() {
        ExceptionCodeSeg<RuntimeException> updateCode = () -> {
            General.bot.checkAccess(User.BACK);
            if (!tasks.isEmpty()) {
                synchronized (tasks) {
                    Iterator.forAll(tasks, BackgroundTask::run);
                }
                removeCompletedTasks();
            } else {
                RobotFramework.backgroundThread.setStatus(ThreadStatus.IDLE);
            }
        };
        RobotFramework.backgroundThread.setExecutionCode(updateCode);
        RobotFramework.backgroundThread.setStatus(ThreadStatus.ACTIVE);
    }
    public final void addBackgroundTask(BackgroundTask task){
        synchronized (tasks){ tasks.add(task); }
    }

    /**
     * Clear task list
     */
    public final void emptyTaskList(){ synchronized (tasks){ tasks.clear(); } }

    /**
     * Remove tasks that are completed
     */
    private void removeCompletedTasks(){
        synchronized (tasks) {
            Iterator.removeCondition(tasks, BackgroundTask::isDone);
        }
    }

}

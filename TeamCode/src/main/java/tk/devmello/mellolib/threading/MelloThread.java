package tk.devmello.mellolib.threading;

import tk.devmello.mellolib.devlogger.Constant;
import tk.devmello.mellolib.devlogger.util.condition.Expectation;
import tk.devmello.mellolib.devlogger.util.condition.Magnitude;
import tk.devmello.mellolib.devlogger.util.misc.ExceptionCatcher;
import tk.devmello.mellolib.threading.util.ThreadStatus;

import java.util.ArrayList;

public class MelloThread extends Thread{
    /**
     * Class for handling and creating thread
     * NOTE: Thread should not be used raw without this class
     */

    /**
     * currentStatus represents the status of the thread
     * NOTE: The thread status is active by default which means it will run the moment it starts
     */
    private volatile ThreadStatus status = ThreadStatus.ACTIVE;
    /**
     * Was an exception thrown?
     */
    private volatile boolean wasExceptionThrown = false;
    /**
     * Arraylist of all MelloThreads
     */
    public static ArrayList<MelloThread> allMelloThreads = new ArrayList<>();
    /**
     * Name of this instance
     */
    private final String name;

    /**
     * Refresh rate in Hz (cycles p
     */
    private final double updateRate;
    private Runnable runnable;
    /**
     * Constructor, creates thread using name and adds it to the arraylist
     * @param name
     */
    public MelloThread(String name) {
        this.name = name;
        this.updateRate = 60;
        this.runnable = () -> {};
        allMelloThreads.add(this);
    }
    /**
     * Constructor, same as above except sets custom update rate
     * @param name
     * @param updateRate
     */
    public MelloThread(String name, double updateRate, Runnable runnable) {
        this.name = name;
        this.updateRate = updateRate;
        this.runnable = runnable;
        allMelloThreads.add(this);
    }

    /**
     * Run method overriden from Thread, all of updateCode will run here
     * This happens until the status is set to disabled through stopUpdating
     * NOTE: This will update according to the thread refresh rate in constants
     */

    @Override
    public void run() {
        while (!status.equals(ThreadStatus.DISABLED)){
            /**
             * Run the update code in a loop
             * NOTE: If the code throws a runtime exception that will be handled and the thread will stop
             */
            try {
                runnable.run();
            } catch (RuntimeException r){
                r.printStackTrace();
                wasExceptionThrown = true;
                stopThread();
            }
            /**
             * Wait according to the thread refresh rate
             */
            ExceptionCatcher.catchInterrupted(()-> sleep((long) (1000.0/updateRate)));
        }
    }
    /**
     * Stop the thread
     */
    public void stopThread() {
        status = ThreadStatus.DISABLED;
    }
    /**
     * Reset all threads
     */
    public static void resetAllThreads() {
        for (MelloThread thread : allMelloThreads) {
            thread.stopThread();
        }
        allMelloThreads.clear();
    }
    /**
     * Set the code that will be run in the thread
     * @param runnable
     */
    public static void setExecutionCode(Runnable runnable) {
        for (MelloThread thread : allMelloThreads) {
            thread.runnable = runnable;
        }
    }
    /**
     * Set the status of the thread
     * @param status
     */
    public void setStatus(ThreadStatus status) {
        this.status = status;
    }
    /**
     * Get the status of the thread
     * @return status
     */
    public ThreadStatus getStatus() {
        return status;
    }

    /**
     * Check if exception was thrown
     * NOTE: This will throw another exception but in the main thread so it is visible and will not just crash the app
     */
    private synchronized void checkException() {
        if (wasExceptionThrown) {
            Constant.logger.fault.warn("Exception was thrown in thread " + name, Expectation.SURPRISING, Magnitude.CATASTROPHIC);
        }
    }
    /**
     * Loop Through all threads for exceptions
     */
    public static synchronized void checkAllExceptions() {
        for (MelloThread thread : allMelloThreads) {
            thread.checkException();
        }
    }
    /**
     * Get number of started threads
     * @return started thread
     */
    public static synchronized int getThreadCount() {
        return allMelloThreads.size();
    }
    /**
     * Get number of active threads
     * @return active threads
     */
    public static synchronized int getNumberOfActiveThreads() {
        int count = 0;
        for (MelloThread thread : allMelloThreads) {
            if (thread.getStatus().equals(ThreadStatus.ACTIVE)) {
                count++;
            }
        }
        return count;
    }
    /**
     * Stop updating all thread
     */
    public static void stopUpdateThreads() {
        for (MelloThread thread : allMelloThreads) {
            if (thread.getStatus().equals(ThreadStatus.ACTIVE)) {
                thread.stopThread();
            }
        }
    }
    /**
     * Start all threads
     */
    public static void startAllThreads() {
        for (MelloThread thread : allMelloThreads) {
            thread.start();
        }
    }

}

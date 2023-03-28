package tk.devmello.robot.util;

public class Access {
    /**
     * Used to store the access that something has to something else
     * This is used for robotfunctions so that different threads can use the robot without interfering
     */


    /**
     * ThreadLocal stores a variable in different thread as separate objects, For example
     * Main Thread -> hasAccess is false
     * Robot Functions Thread -> hasAccess is true
     * If isAllowed is called from main thread it will return false
     * If isAllowed is called from the robotfunctions thread it will return true
     * NOTE: This is at the same time (hasAccess is in two different states depending on the thread)
     */
    private final ThreadLocal<Boolean> hasAccess = new ThreadLocal<>();

    /**
     * Constructor, denys by default
     * NOTE: If you want to give access you must explicitly call allow
     */
    public Access(){
        deny();
    }

    /**
     * Allow, sets hasAccess to true in the thread from which it is called
     */
    public synchronized void allow(){
        hasAccess.set(true);
    }

    /**
     * Opposite of allow
     * @link allow
     */
    public synchronized void deny(){
        hasAccess.set(false);
    }
    /**
     * Is access allowed?
     */
    public synchronized boolean isAllowed(){
        if (hasAccess != null) {
            return hasAccess.get();
        }else{
            return false;
        }
    }

}

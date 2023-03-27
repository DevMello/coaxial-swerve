package tk.devmello.mellolib.devlogger;


import tk.devmello.mellolib.devlogger.util.condition.Expectation;
import tk.devmello.mellolib.devlogger.util.condition.Magnitude;

public class Fault {

    /**
     * Is the fault in unsafe mode (i.e. the fault will not throw exceptions even in check)
     * NOTE: this should only be set to true when the robot is being run in the competition
     */
    private final boolean unsafeMode = false;
    protected static Logger logger = new Logger("Fault Logger", "Fault Class");

    /**
     * 1. Warn that something went wrong with a message
     * 2. Warn with more details
     * 3. Warn that something has gone wrong with a message if test does not match the correct value
     * 4. Same as above except more detailed
     * @param msg
     */
    public void warn(String msg){
        createFault(getName(msg), true, false, false);
    }
    public void warn(String msg, Expectation e, Magnitude m){
        createFault(getName(msg, e, m), true, false, false);
    }
    public void warn(String msg, boolean test, boolean correct){
        createFault(getName(msg), test != correct, false, false);
    }
    public void warn(String msg, Expectation e, Magnitude m, boolean test, boolean correct){
        createFault(getName(msg, e, m), test != correct, false, false);
    }

    /**
     * 1. Same as warn except throws exception, NOTE: Only use when something major has gone wrong or is important
     * 2. Same as above with more details
     * 3. Same as warn except throws an exception if test does not match the correct value
     * 4. Same as above with more details
     * @param msg
     */
    public void check(String msg) {
        createFault(getName(msg), true, true, false);
    }
    public void check(String msg, Expectation e, Magnitude m) {
        createFault(getName(msg, e, m), true, true, false);
    }
    public void check(String msg, boolean test, boolean correct) {
        createFault(getName(msg), test != correct, true, false);
    }
    public void check(String msg, Expectation e, Magnitude m, boolean test, boolean correct){
        createFault(getName(msg, e, m), test != correct, true, false);
    }

    /**
     * 1. Same as warn except logs instead of telemetry
     * 2,3,4 see warn
     * @param msg
     */
    public void log(String msg){
        createFault(getName(msg), true, false, true);
    }
    public void log(String msg, Expectation e, Magnitude m){
        createFault(getName(msg, e, m), true, false, true);
    }
    public void log(String msg, boolean test, boolean correct){
        createFault(getName(msg), test != correct, false, true);
    }
    public void log(String msg, Expectation e, Magnitude m, boolean test, boolean correct){
        createFault(getName(msg, e, m), test != correct, false, true);
    }


    /**
     * Gets the name given a message
     * @param msg
     * @return
     */
    private String getName(String msg){
        return "Msg: " + msg;
    }

    /**
     * Gets the name given a message and expectation and magnitude
     * @param msg
     * @param e
     * @param m
     * @return
     */
    private String getName(String msg, Expectation e, Magnitude m){
        return getName(msg) + " Exp: " + e.toString() + " Mag: " + m.toString();
    }

    /**
     * Create a fault using a message, display the faultNum if in unsafe mode
     * @param out
     * @param failed
     * @param createException
     */
    private void createFault(String out, boolean failed, boolean createException, boolean logOnly){
        if(failed){
            if(logOnly) {
                logger.record(out, "Please check your code for logical errors");
            }else {
                logger.showAndRecord(out, "Please check your code for logical errors");
            }
            if(!unsafeMode && createException){
                RuntimeException exception = new RuntimeException(" "+out);
                exception.printStackTrace();
                throw exception;
            }
        }
    }
}
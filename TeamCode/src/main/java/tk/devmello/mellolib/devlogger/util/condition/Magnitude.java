package tk.devmello.mellolib.devlogger.util.condition;

public enum Magnitude{
    /**
     * What is the magnitude of the fault?
     */


    /**
     * Very minor and does not affect anything
     */
    NEGLIGIBLE,

    /**
     * May affect some things but will not cause the robot to fail
     */
    MINOR,

    /**
     * Potential cause the robot to fail and should be fixed eventually
     */
    MODERATE,

    /**
     * Probably will cause robot failure
     */
    MAJOR,

    /**
     * Definite will cause robot failure and should be fixed immediately
     */
    CRITICAL,

    /**
     * Dangerous code that will injure if not destroy the robot
     */
    CATASTROPHIC
}
package tk.devmello.mellolib.devlogger.util.condition;

public enum Expectation{
    /**
     * How expected is the fault?
     */


    /**
     * This will happen on the next run
     */
    TRIVIAL,

    /**
     * This will happen eventually
     */
    OBVIOUS,

    /**
     * This is likely to happen
     */
    EXPECTED,

    /**
     * This may happen
     */
    SURPRISING,

    /**
     * This should not happen
     */
    UNEXPECTED,

    /**
     * This cannot happen (less than 0.01% probability)
     */
    INCONCEIVABLE
}

package tk.devmello.robot.util;

public class Constants {
    private Constants(){}
    /**
     * Default refresh rate for threads, tested at 100 and 60 hz
     */
    public static final int DEFAULT_THREAD_REFRESH_RATE = 60; //hz

    /**
     * Robot functions thread refresh rate
     */
    public static final int ROBOT_FUNCTIONS_REFRESH_RATE = 700;

    /**
     * Background thread update rate (note this is the highest possible)
     */
    public static final int BACKGROUND_THREAD_REFRESH_RATE = 1000; //hz

    /**
     * Odometry thread update rate
     */
    public static final int ODOMETRY_THREAD_REFRESH_RATE = 500; //hz

    /**
     * Independent thread update rate
     */
    public static final int INDEPENDENT_THREAD_REFRESH_RATE = 1000; //hz

    /**
     *  Minimum  refresh rate allowed before robot is considered to be lagging
     */
    public static final int MINIMUM_REFRESH_RATE = 30; //hz
    public static final double ORBITAL_TICKS_PER_REV = 537.6;
    public static final double ENCODER_TICKS_PER_REV = 8192;

    public static final double DEFAULT_VOLTAGE = 12.5;

    public static final String VUFORIA_KEY = "ASEyqP7/////AAABmb4cSklfmUjBkxJayTfU3Woug32Gg9HrKpH+MBhQdW6OxZu5Fd+RHsBaSsL42WahxSOgd9FJTo4DVzuJaF9MUSjbE/Vy/MkBzjypT5O320DFwHzD8+RHFfWofe0zqC/sk8zBZCDtbPBGwhVIKPm8vrnOQBExz+Acru9akg3rGnVdhfiD6Qs6vuLPe+PVsR0diewGO93rSWI5mHOm3BNyaTfDru3b2qtCAwRsje8uzDLnus4PCEb7mZWE5NJiEMIsZlPUrNd0AllM1SnXVpUtHBwqmTQNCBvbbLP+glWpXlyWzanGq244GHkBT0YD54OAd84XJvTsykplKAXazA+FozkpPENdQVZd+oN4RvCkvZ0a";
    public static final double INCH_TO_CM = 2.54;
    public static final double VUFORIA_TARGET_HEIGHT_CM = 6*INCH_TO_CM;
}

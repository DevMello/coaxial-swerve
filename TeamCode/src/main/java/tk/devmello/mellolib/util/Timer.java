package tk.devmello.mellolib.util;


import tk.devmello.mellolib.devlogger.Constant;
import tk.devmello.mellolib.devlogger.util.condition.Expectation;
import tk.devmello.mellolib.devlogger.util.condition.Magnitude;

import static tk.devmello.robot.bot.General.gameTime;

public class Timer {
    /**
     * Timer class, to use create a timer object in your code as follows:
     * Timer timerName = new Timer();
     *
     * NOTE: Do NOT do this
     * Timer timerName = new timerName();
     * or this
     * timerName Timer = new Timer();
     * or this
     * Timer Timer = new Timer();
     * or this
     * timer timerName = new timer();
     *
     * Use by calling timerName.reset(); and then timerName.seconds() to get the time in seconds
     */


    /**
     * Start time in seconds (when the timer was last reset)
     */
    private double startTime = 0;
    /**
     * Has the timer ever been reset?
     */
    private boolean hasBeenReset = false;
    /**
     * Reset the timer by setting the start time
     */
    public void reset(){
        startTime = gameTime == null ? 0 : gameTime.seconds();
        hasBeenReset = true;
    }
    public void set(double time){
        startTime = gameTime == null ? 0 : gameTime.seconds() -time;
        hasBeenReset = true;
    }
    /**
     * Gets the seconds since last time the timer was reset
     * NOTE: If the timer has never been reset and seconds is called then an warning will be issued
     * @return seconds
     */
    public double seconds(){
        Constant.logger.fault.warn("Used timer before reset", Expectation.SURPRISING, Magnitude.CRITICAL, hasBeenReset, true);
        return gameTime.seconds()-startTime;
    }


}
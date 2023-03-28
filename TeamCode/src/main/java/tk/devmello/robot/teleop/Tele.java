package tk.devmello.robot.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import tk.devmello.robot.bot.RobotUser;
import tk.devmello.robot.util.Common;
import tk.devmello.robot.util.mode.Modes;

public abstract class Tele extends OpMode implements Common, RobotUser, Modes {
    /**
     * Base class for teleops
     * NOTE: If you are creating a real teleop then call activate in initTele
     */


    /**
     * Init method runs when the user clicks the init button to run a teleop
     */
    public void initTele() {
    }

    /**
     * Start method runs when the user clicks the start button after init
     */
    public void startTele() {
    }

    /**
     * Loop method runs over and over after start
     */
    public abstract void loopTele();

    /**
     * Stop method runs when the program ends
     */
    public void stopTele() {
    }


    /**
     * Internal final teleop methods
     * NOTE: Do not use or override these
     */

    @Override
    public final void init() {
        reference(this);
        //activate();
        initTele();
    }

    @Override
    public final void start() {
        ready();
        startTele();
    }

    @Override
    public final void loop() {
        loopTele();
        update(true);
    }

    @Override
    public final void stop() {
        stopTele();
    }
}

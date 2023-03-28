package tk.devmello.robot.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import tk.devmello.mellolib.devlogger.util.storage.Storage;
import tk.devmello.robot.bot.General;
import tk.devmello.robot.bot.SwerveBot;

public interface Common {
    default void reference(OpMode thisOpMode){
        /**
         * Initialize all of the objects from the opmode
         */
        General.hardwareMap = thisOpMode.hardwareMap;
        General.telemetry = thisOpMode.telemetry;
        General.gamepad1 = thisOpMode.gamepad1;
        General.gamepad2 = thisOpMode.gamepad2;
        /**
         * Create the gameTime
         */
        General.gameTime = new ElapsedTime();
        /**
         * Create the gamepadhanlders from the gamepads
         */
//        General.gph1 = new GamepadHandler(General.gamepad1);
//        General.gph2 = new GamepadHandler(General.gamepad2);
//        /**
//         * Create the debugging tools
//         */
//        General.fault = new Fault();
//        General.sync = new Synchroniser();
//        General.log = new Logger();
        /**
         * Get the main user
         * NOTE: the user is automatically set from the type of opMode
         */
        General.mainUser = User.getUserFromTypeOfOpMode(thisOpMode);
        /**
         * Set the view ID
         */
//        General.cameraMonitorViewId = Cameras.getCameraMonitorViewId();
        /**
         * Set the voltage scale
         */
        //General.voltageScale = RobotFramework.calculateVoltageScale(RobotFramework.getBatteryVoltage());
        /**
         * Create the storage
         */
        General.storage = new Storage();
        /**
         * Create the robot, and then the modules, stages, and automodules
         */
        General.bot = new SwerveBot();
        /**
         * Initialize the robot
         */
        General.bot.init();
    }

    /**
     * Activate sets the field side and thus should only be used in teleop or auton
     * Also shows telemetry to display that the robot is ready
     */
//    default void activate(){
//        General.sync.logReady();
//    }

    /**
     * Starts the robot and clears the telemetry
     */
    default void ready(){
        General.bot.start();
        //General.sync.resetDelay();
        General.logger.consoleLogInfo("Robot is ready");
    }

    /**
     * Updates the telemetry, checks the access for bot for the main user (either teleop or auton)
     * Runs the gamepad handlers
     * @param showTelemetry
     */
    default void update(boolean showTelemetry){
        General.bot.update();
//        General.gph1.run();
//        General.gph2.run();
//        General.sync.update();
//        if(showTelemetry){
//            General.log.showTelemetry();}
    }

    /**
     * Stops the robot, halts all of the motors (in stop), checks if common is used properly, shows the logs, and saves the storage times
     */
    default void end(){
        General.bot.stop();
//        General.sync.logDelay();
//        General.log.showLogs();
        General.storage.saveItems();
    }
}

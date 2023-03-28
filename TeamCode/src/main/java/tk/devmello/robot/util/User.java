package tk.devmello.robot.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import tk.devmello.mellolib.devlogger.util.condition.Expectation;
import tk.devmello.mellolib.devlogger.util.condition.Magnitude;
import tk.devmello.robot.bot.General;

public enum User {
    /**
     * A user enum which represents what is using something, either teleop, auton, robotfunctions, or none
     * This is used in order to control which threads have access to move parts of the robot
     */
    TELE,
    AUTO,
    ROFU,
    BACK,
    INDP,
    NONE;

    /**
     * Get the user from the type of opmode, if the opmode is a linear opmode then set it to auton
     * Used to automatically set the main user to AUTO or TELE
     * NOTE: If null is passed in this method returns NONE
     * @param opMode
     * @return user
     */

    public static User getUserFromTypeOfOpMode(OpMode opMode){
        if(opMode instanceof LinearOpMode){
            return User.AUTO;
        }else if(opMode != null){
            return User.TELE;
        }else{
            General.logger.fault.warn("The main user is set to NONE", Expectation.INCONCEIVABLE, Magnitude.CRITICAL);
            return User.NONE;
        }
    }
}

package tk.devmello.robot.bot;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import tk.devmello.mellolib.devlogger.Logger;
import tk.devmello.mellolib.devlogger.debugging.Log;
import tk.devmello.mellolib.devlogger.util.storage.Storage;
import tk.devmello.robot.util.User;

public class General {

    /**
     * Bot initialization
     */
    public static SwerveBot bot;

    public static Logger logger = new Logger("General Robot");
    /**
     * Hardware map object, has all of the hardware in the robot like the DcMotors and Servos
     */
    public static HardwareMap hardwareMap;
    /**
     * Telemetry object, used to display output to the phone (like system.out.print())
     */
    public static Telemetry telemetry;
    /**
     * Gamepad objects (controllers), there are two, one is conected using start+A and the other with start+B
     */
    public static Gamepad gamepad1;
    public static Gamepad gamepad2;
    /**
     * Elapsed timer (a timer) object that stores the gametimer, is reset on init
     */
    public static ElapsedTime gameTime;
    /**
     * The main user
     * NOTE: This should either be TELE or AUTO
     */
    public static User mainUser;

    public static Log log = new Log("General Robot");

    public static Storage storage = new Storage();



}

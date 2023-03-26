package tk.devmello.swerve.coaxial;

import android.graphics.PointF;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class SwerveDrive {

    public static final double WHEEL_CIRCUMFERENCE = 2.75590551 * Math.PI;
    //may be changed
    public static final double TICKS_PER_ROTATION = 0;
    //lowkey need to tune this? motor encoder of motor on pod tick btw
    public static final double MAX_TICKS_PER_SECOND = 2500;

    //chassis area(basically)
    public static final double CHASSIS_RAD_SQUARED = 0;
    public static final double MAX_DRIVE_SPEED = MAX_TICKS_PER_SECOND * WHEEL_CIRCUMFERENCE / TICKS_PER_ROTATION;
    public static final double MAX_ANGULAR_SPEED = MAX_DRIVE_SPEED / Math.sqrt(CHASSIS_RAD_SQUARED);

    CRServo[] crServos = new CRServo[4];    //Steering
    DcMotor[] encoders = new DcMotor[4];    //Encoders to monitor steering
    DcMotor[] motors = new DcMotor[4];      //Drive motors

    //Positions of the four drive wheels in robot-coordinate system, in inches
    public static final PointF[] WHEEL_POS = new PointF[]{
            new PointF(0, 0),           //Back Left
            new PointF(0, 0),            //Front Left
            new PointF(0, 0),             //Front Right
            new PointF(0, 0)             //Back Right
    };


    /**
     *
     * @param crServos
     * @param encoders
     * @param motors
     * Letter Map
     * B -> Back
     * F -> Front
     * L -> Left
     * R -> Right
     * M -> Motor
     * C -> Servo
     * E -> Encoder
     *
     */

    public SwerveDrive(CRServo[] crServos, DcMotor[] encoders, DcMotor[] motors) {
        this.crServos = crServos;
        this.encoders = encoders;
        this.motors = motors;
        motors[0].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void init(HardwareMap hardwareMap){
        String[] crServoNames = new String[] {"BLC", "FLC", "FRC", "BRC"};
        for (int i=0; i<4; i++) crServos[i] = (CRServo)hardwareMap.get(CRServo.class, crServoNames[i]);
        String[] encoderNames = new String[] {"BLE", "FLE", "FRE", "BRE"};
        for (int i=0; i<4; i++) encoders[i] = (DcMotor)hardwareMap.get(DcMotor.class, encoderNames[i]);
        String[] motorNames = new String[] {"BLM", "FLM", "FRM", "BRM"};
        for (int i=0; i<4; i++) motors[i] = (DcMotor)hardwareMap.get(DcMotor.class, motorNames[i]);

    }
    /**
     * Sets the robot drive speed (linear and angular)
     * @param vx    Speed (inches per sec) in X direction (positive is rightward)
     * @param vy    Speed (inches per sec) in Y direction (positive is forward)
     * @param va    Angular speed (radians per sec)   (positive is counter-clockwise)
     */
    public void setDriveSpeed(double vx, double vy, double va) {

        double[] wheelPowers = new double[4];

        //Determine the power needed for each wheel.
        for (int i=0; i<4; i++){
            double wheelSpeed = Math.sqrt(vx * vx + vy * vy + va * va * CHASSIS_RAD_SQUARED
                    + 2 * va * (vy * WHEEL_POS[i].x - vx * WHEEL_POS[i].y));
            wheelPowers[i] = (wheelSpeed / WHEEL_CIRCUMFERENCE) * TICKS_PER_ROTATION / MAX_TICKS_PER_SECOND;
        }
        //Wheel powers must be in the range -1 to +1. If they are not, then we must scale down the values
        //of each wheel power, and also the values of vx, vy, and va
        double max = 1.0;
        for (int i=0; i<4; i++) max = Math.max(max, Math.abs(wheelPowers[i]));
        if (max > 1.0){
            vx /= max;
            vy /= max;
            va /= max;
            for (int i=0; i<4; i++) wheelPowers[i] /= max;
        }
        for (int i = 0; i < 4; i++) {
            double targetSteer = normalizeRadians(Math.atan2(vy + va * WHEEL_POS[i].x, vx - va * WHEEL_POS[i].y) - Math.PI/2);
            boolean reversed = setSteer(i, targetSteer);
            if (reversed) wheelPowers[i] *= -1;
            motors[i].setPower(wheelPowers[i]);
        }
    }

    private double getSteerRadians(int i){
        return normalizeRadians(2.0 * Math.PI * encoders[i].getCurrentPosition()/TICKS_PER_ROTATION);
    }

    private boolean setSteer(int i, double targetSteer){
        double currentSteer = getSteerRadians(i);
        double offset = normalizeRadians(targetSteer - currentSteer);
        boolean result = false;
        if (Math.abs(offset) > Math.PI/2){
            result = true;
            offset = normalizeRadians(offset + Math.PI);
        }
        double steerPower = Range.clip(4.0*offset/Math.PI, -1, 1);
        crServos[i].setPower(steerPower);
        return result;
    }

    public static double normalizeRadians(double radians) {
        double temp = (radians + Math.PI) / (2.0 * Math.PI);
        return (temp - Math.floor(temp) - 0.5) * 2.0 * Math.PI;
    }
}

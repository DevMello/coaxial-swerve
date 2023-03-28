package tk.devmello.robot.hardware.subsystems;

import android.graphics.PointF;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import tk.devmello.robot.bot.General;
import tk.devmello.robot.hardware.RobotPart;


public class Drive extends RobotPart {
    public static final double WHEEL_CIRCUMFERENCE = 2.75590551 * Math.PI;
    public static final double TICKS_PER_ROTATION = 283.6;
    public static final double MAX_TICKS_PER_SECOND = 2500;
    public static final double CHASSIS_RAD_SQUARED = 1;
    public static final double MAX_DRIVE_SPEED = MAX_TICKS_PER_SECOND * WHEEL_CIRCUMFERENCE / TICKS_PER_ROTATION;
    public static final double MAX_ANGULAR_SPEED = MAX_DRIVE_SPEED / Math.sqrt(CHASSIS_RAD_SQUARED);
    public static final PointF[] WHEEL_POS = new PointF[]{
            new PointF(0, 0),           //Back Left
            new PointF(0, 0),            //Front Left
            new PointF(0, 0),             //Front Right
            new PointF(0, 0)             //Back Right
    };
    CRServo[] crServos = new CRServo[4];    //Steering
    DcMotor[] encoders = new DcMotor[4];    //Encoders to monitor steering
    DcMotor[] motors = new DcMotor[4];

    public static double normalizeRadians(double radians) {
        double temp = (radians + Math.PI) / (2.0 * Math.PI);
        return (temp - Math.floor(temp) - 0.5) * 2.0 * Math.PI;
    }

    @Override
    public void init() {
        String[] crServoNames = new String[]{"BLC", "FLC", "FRC", "BRC"};
        for (int i = 0; i < 4; i++) crServos[i] = General.hardwareMap.get(CRServo.class, crServoNames[i]);
        String[] encoderNames = new String[]{"BLE", "FLE", "FRE", "BRE"};
        for (int i = 0; i < 4; i++) encoders[i] = General.hardwareMap.get(DcMotor.class, encoderNames[i]);
        String[] motorNames = new String[]{"BLM", "FLM", "FRM", "BRM"};
        for (int i = 0; i < 4; i++) motors[i] = General.hardwareMap.get(DcMotor.class, motorNames[i]);
    }
    @Override
    public void halt() {
        for (int i = 0; i < 4; i++) {
            crServos[i].setPower(0);
            motors[i].setPower(0);
        }
    }

    public void update(double f, double s, double t) {
        move(f, s, t);
    }

    public void start() {

    }

    @Override
    public void move(double vy, double vx, double va) {
        double[] wheelPowers = new double[4];

        //Determine the power needed for each wheel.
        for (int i = 0; i < 4; i++) {
            double wheelSpeed = Math.sqrt(vx * vx + vy * vy + va * va * CHASSIS_RAD_SQUARED
                    + 2 * va * (vy * WHEEL_POS[i].x - vx * WHEEL_POS[i].y));
            wheelPowers[i] = (wheelSpeed / WHEEL_CIRCUMFERENCE) * TICKS_PER_ROTATION / MAX_TICKS_PER_SECOND;
        }
        //Wheel powers must be in the range -1 to +1. If they are not, then we must scale down the values
        //of each wheel power, and also the values of vx, vy, and va
        double max = 1.0;
        for (int i = 0; i < 4; i++) max = Math.max(max, Math.abs(wheelPowers[i]));
        if (max > 1.0) {
            vx /= max;
            vy /= max;
            va /= max;
            for (int i = 0; i < 4; i++) wheelPowers[i] /= max;
        }
        for (int i = 0; i < 4; i++) {
            double targetSteer = normalizeRadians(Math.atan2(vy + va * WHEEL_POS[i].x, vx - va * WHEEL_POS[i].y) - Math.PI / 2);
            boolean reversed = setSteer(i, targetSteer);
            if (reversed) wheelPowers[i] *= -1;
            motors[i].setPower(wheelPowers[i]);
            General.logger.consoleLogInfo(wheelPowers[i] + ": " + motors[i].getDeviceName());
            General.logger.fault.log(wheelPowers[i] + ": " + motors[i].getDeviceName());
        }
    }

    private double getSteerRadians(int i) {
        return normalizeRadians(2.0 * Math.PI * encoders[i].getCurrentPosition() / TICKS_PER_ROTATION);
    }

    private boolean setSteer(int i, double targetSteer) {
        double currentSteer = getSteerRadians(i);
        double offset = normalizeRadians(targetSteer - currentSteer);
        boolean result = false;
        if (Math.abs(offset) > Math.PI / 2) {
            result = true;
            offset = normalizeRadians(offset + Math.PI);
        }
        double steerPower = Range.clip(4.0 * offset / Math.PI, -1, 1);
        crServos[i].setPower(steerPower);
        General.logger.consoleLogInfo(steerPower + ": " + crServos[i].getDeviceName());
        General.logger.fault.log(steerPower + ": " + crServos[i].getDeviceName());
        return result;
    }

}

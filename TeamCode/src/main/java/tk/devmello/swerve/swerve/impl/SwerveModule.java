package tk.devmello.swerve.swerve.impl;

import android.graphics.PointF;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import tk.devmello.swerve.hardware.CRServo;

public class SwerveModule {

    public static final double WHEEL_CIRCUMFERENCE = 2.75590551 * Math.PI;
    //may be changed
    public static final double TICKS_PER_ROTATION = 0;
    //lowkey need to tune this? motor encoder of motor on pod tick btw
    public static final double MAX_TICKS_PER_SECOND = 2500;

    //chassis area(basically)
    public static final double CHASSIS_RAD_SQUARED = 0;
    public static final double MAX_DRIVE_SPEED = MAX_TICKS_PER_SECOND * WHEEL_CIRCUMFERENCE / TICKS_PER_ROTATION;
    public static final double MAX_ANGULAR_SPEED = MAX_DRIVE_SPEED / Math.sqrt(CHASSIS_RAD_SQUARED);

    public DcMotor driveMotor;
    public DcMotor encoder;
    public CRServo turnServo;

    public static final PointF[] WHEEL_POS = new PointF[]{
            new PointF(0, 0),           //Back Left
            new PointF(0, 0),            //Front Left
            new PointF(0, 0),             //Front Right
            new PointF(0, 0)             //Back Right
    };
    public SwerveModule(CRServo turnServo, DcMotor encoder, DcMotor driveMotor) {
        this.turnServo = turnServo;
        this.encoder = encoder;
        this.driveMotor = driveMotor;
    }

    /**
     *
     * @param vx
     * @param vy
     * @param va
     * @param module
     * This class demonstrates how a single SwerveModule would work
     * The class is not actually being used in the SwerveDrive class but later in the future it might.
     *
     */
    public void drive(double vx, double vy, double va, int module) {
        double wheelPower;
        double wheelSpeed = Math.sqrt(vx * vx + vy * vy + va * va * CHASSIS_RAD_SQUARED
                + 2 * va * (vy * WHEEL_POS[module].x - vx * WHEEL_POS[module].y));
        wheelPower = (wheelSpeed / WHEEL_CIRCUMFERENCE) * TICKS_PER_ROTATION / MAX_TICKS_PER_SECOND;

        double max = 1.0;

        max = Math.max(max, Math.abs(wheelPower));
        if (max > 1.0){
            vx /= max;
            vy /= max;
            va /= max;
            wheelPower /= max;
        }
        double targetSteer = normalizeRadians(Math.atan2(vy + va * WHEEL_POS[module].x, vx - va * WHEEL_POS[module].y) - Math.PI/2);
        boolean reversed = setSteer(module, targetSteer);
        if (reversed) wheelPower *= -1;
        driveMotor.setPower(wheelPower);
    }
    private double getSteerRadians(int i){
        return normalizeRadians(2.0 * Math.PI * encoder.getCurrentPosition()/TICKS_PER_ROTATION);
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
        turnServo.set(steerPower);
        return result;
    }

    public static double normalizeRadians(double radians) {
        double temp = (radians + Math.PI) / (2.0 * Math.PI);
        return (temp - Math.floor(temp) - 0.5) * 2.0 * Math.PI;
    }
}

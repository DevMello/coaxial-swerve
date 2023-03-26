package tk.devmello.swerve.coaxial.impl;

import com.acmerobotics.roadrunner.drive.SwerveDrive;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.util.Range;
import tk.devmello.swerve.controller.PController;

//import servo from
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import tk.devmello.swerve.controller.PIDFController;
import tk.devmello.swerve.hardware.CRServo;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.normalizeRadians;

public class CoaxialModule {
    private static final boolean MOTOR_FLIPPING = true;
    public static double P = 0, I = 0, D = 0;
    public static double K_STATIC = 0.04;
    public static double MAX_SERVO = 1, MAX_MOTOR = 1;

    public static double WHEEL_RADIUS = 0; // in
    public static double GEAR_RATIO; // need hardware cad before calculation
    public static final double TICKS_PER_REV = 28;

    public Motor driveMotor;
    public CRServo turnServo;
    private PController pController;
    private MotorEx encoder;
    private double angle;

    private PIDFController rotationController;
    private double target = 0.0;
    private double position = 0.0;
    private boolean wheelFlipped = true;

    public CoaxialModule(CRServo turn, Motor drive, double p, MotorEx encoder) {
        turnServo = turn;
        (turnServo).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        driveMotor = drive;
        pController = new PController(p);
        this.encoder = encoder;
        angle = 0;
        rotationController = new PIDFController(P, I, D, 0);
        driveMotor.resetEncoder();
    }
    public void driveModule(double speed, double angle) {
        driveMotor.set(speed);
        pController.pControl(turnServo, angle, this.angle);
        this.angle = angle;
    }
    public void disable() {
        turnServo.disable();
        driveMotor.disable();
    }
    public void stopMotor() {
        turnServo.stopMotor();
        driveMotor.stopMotor();
    }
    public void setMode(Motor.RunMode runMode) {
        driveMotor.setRunMode(runMode);
    }
    public void setZeroPowerBehavior(Motor.ZeroPowerBehavior zeroPowerBehavior) {
        driveMotor.setZeroPowerBehavior(zeroPowerBehavior);
    }
    public double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }
    public double getTargetRotation() {
        return normalizeRadians(target - Math.PI);
    }

    public double getModuleRotation() {
        return normalizeRadians(position - Math.PI);
    }
    public double getWheelPosition() {
        return encoderTicksToInches(driveMotor.getCurrentPosition());
    }
}


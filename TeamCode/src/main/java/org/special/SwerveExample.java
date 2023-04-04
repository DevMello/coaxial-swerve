package org.special;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import me.wobblyyyy.pathfinder2.Pathfinder;
import me.wobblyyyy.pathfinder2.control.Controller;
import me.wobblyyyy.pathfinder2.control.GenericTurnController;
import me.wobblyyyy.pathfinder2.control.SimpleSwerveModuleController;
import me.wobblyyyy.pathfinder2.control.SplineController;
import me.wobblyyyy.pathfinder2.drive.SwerveDrive;
import me.wobblyyyy.pathfinder2.drive.SwerveModule;
import me.wobblyyyy.pathfinder2.follower.FollowerGenerator;
import me.wobblyyyy.pathfinder2.follower.generators.GenericFollowerGenerator;
import me.wobblyyyy.pathfinder2.geometry.Angle;
import me.wobblyyyy.pathfinder2.geometry.Translation;
import me.wobblyyyy.pathfinder2.math.Spline;
import me.wobblyyyy.pathfinder2.odometrycore.ThreeWheelOdometry;
import me.wobblyyyy.pathfinder2.robot.Drive;
import me.wobblyyyy.pathfinder2.robot.Odometry;
import me.wobblyyyy.pathfinder2.robot.Robot;
import me.wobblyyyy.pathfinder2.robot.components.Motor;
import me.wobblyyyy.pathfinder2.robot.simulated.SimulatedDrive;
import me.wobblyyyy.pathfinder2.robot.simulated.SimulatedMotor;
import me.wobblyyyy.pathfinder2.robot.simulated.SimulatedOdometry;
import me.wobblyyyy.pathfinder2.utils.DualJoystick;
import me.wobblyyyy.pathfinder2.utils.Gamepad;
import me.wobblyyyy.pathfinder2.utils.Joystick;

import java.util.function.Supplier;
@TeleOp(name = "Swerve Pathfinder", group = "TeleOp")
public class SwerveExample extends LinearOpMode {

    private final SimulatedMotor frontRightDrive = new SimulatedMotor();
    private final SimulatedMotor frontLeftDrive = new SimulatedMotor();
    private final SimulatedMotor backRightDrive = new SimulatedMotor();
    private final SimulatedMotor backLeftDrive = new SimulatedMotor();
    private final SimulatedMotor frontRightSteer = new SimulatedMotor();
    private final SimulatedMotor frontLeftSteer = new SimulatedMotor();
    private final SimulatedMotor backRightSteer = new SimulatedMotor();
    private final SimulatedMotor backLeftSteer = new SimulatedMotor();
    private final Angle defaultPos = new Angle(90);
    private final SwerveModule frontRight = new SwerveModule((Motor) frontRightSteer, (Motor) frontRightDrive, (Supplier<Angle>) defaultPos);
    private final SwerveModule frontLeft = new SwerveModule(frontLeftSteer, frontLeftDrive, (Supplier<Angle>) defaultPos);
    private final SwerveModule backRight = new SwerveModule(backRightSteer, backRightDrive, (Supplier<Angle>) defaultPos);
    private final SwerveModule backLeft = new SwerveModule(backLeftSteer, backLeftDrive, (Supplier<Angle>) defaultPos);
    private final Controller controller = new GenericTurnController(0.01);
    private final Drive drive = new SwerveDrive(frontRight, frontLeft, backRight, backLeft, controller, 0.2);
    private final Odometry odometry = new SimulatedOdometry();
    private final FollowerGenerator followerGenerator = new GenericFollowerGenerator(
            controller
    );
    private final Robot robot = new Robot(drive, odometry);

    private final Pathfinder pathfinder = new Pathfinder(
            robot,
            followerGenerator
    );
    private final Joystick rightJoystick = new Joystick(
            this::rightStickX,
            this::rightStickY
    );
    private final Joystick leftJoystick = new Joystick(
            this::leftStickX,
            this::leftStickY
    );
    private final DualJoystick joysticks = new DualJoystick(
            leftJoystick,
            rightJoystick
    );
    private final Gamepad gamepad = new Gamepad();
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {
            double vx = leftJoystick.getX();
            double vy = leftJoystick.getY() * -1;
            double vz = rightJoystick.getX();

            Translation translation = new Translation(vx, vy, vz);
            pathfinder.setTranslation(translation);
        }
    }

    public double leftStickX() {
        return gamepad1.left_stick_x;
    }

    public double leftStickY() {
        return gamepad1.left_stick_y;
    }

    public double rightStickX() {
        return gamepad1.right_stick_x;
    }

    public double rightStickY() {
        return gamepad1.right_stick_y;
    }
}

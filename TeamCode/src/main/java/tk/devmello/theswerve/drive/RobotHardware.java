package tk.devmello.theswerve.drive;



import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import tk.devmello.theswerve.drive.Global.*;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import tk.devmello.theswerve.drive.swerve.SwerveDrivetrain;
import tk.devmello.theswerve.math.Pose;

import javax.annotation.concurrent.GuardedBy;

public class RobotHardware {
//    public MotorEx liftLeft;
//    public MotorEx liftRight;
//    public MotorEx extension;

    public DcMotorEx frontLeftMotor;
    public DcMotorEx frontRightMotor;
    public DcMotorEx backLeftMotor;
    public DcMotorEx backRightMotor;

//    public Servo claw;
//    public Servo turret;
//    public Servo pivot;
//    public Servo fourbarLeft;
//    public Servo fourbarRight;
//    public Servo latch;

    public CRServo frontLeftServo;
    public CRServo frontRightServo;
    public CRServo backLeftServo;
    public CRServo backRightServo;

    public DcMotorEx frontLeftEncoder;
    public DcMotorEx frontRightEncoder;
    public DcMotorEx backLeftEncoder;
    public DcMotorEx backRightEncoder;

    public Motor.Encoder liftEncoder;
    public Motor.Encoder intakeEncoder;
    public Motor.Encoder parallelPod;
    public Motor.Encoder perpindicularPod;

    private final Object imuLock = new Object();
    @GuardedBy("imuLock")
    public BNO055IMU imu;
    private Thread imuThread;
    private double imuAngle = 0;
    private double imuOffset = 0;

    public DigitalChannel clawSensor;

    public OpenCvCamera camera;

    public VoltageSensor voltageSensor;

    public OpenCvPipeline pipeline;

    private static RobotHardware instance = null;

    public boolean enabled;

    public static RobotHardware getInstance() {
        if (instance == null) {
            instance = new RobotHardware();
        }
        instance.enabled = true;
        return instance;
    }

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        if (Global.USING_IMU) {
            synchronized (imuLock) {
                imu = hardwareMap.get(BNO055IMU.class, "imu");
                BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
                parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
                imu.initialize(parameters);
            }
        }



        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");



        frontLeftServo = hardwareMap.get(CRServo.class, "frontLeftServo");
        frontRightServo = hardwareMap.get(CRServo.class, "frontRightServo");
        backLeftServo = hardwareMap.get(CRServo.class, "backLeftServo");
        backRightServo = hardwareMap.get(CRServo.class, "backRightServo");

        frontLeftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightServo.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightServo.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftEncoder = hardwareMap.get(DcMotorEx.class, "frontLeftEncoder");
        frontRightEncoder = hardwareMap.get(DcMotorEx.class, "frontRightEncoder");
        backLeftEncoder = hardwareMap.get(DcMotorEx.class, "backLeftEncoder");
        backRightEncoder = hardwareMap.get(DcMotorEx.class, "backRightEncoder");

        liftEncoder = new MotorEx(hardwareMap, "frontLeftMotor").encoder;
        liftEncoder.setDirection(Motor.Direction.REVERSE);
        intakeEncoder = new MotorEx(hardwareMap, "frontRightMotor").encoder;

        parallelPod = new MotorEx(hardwareMap, "backLeftMotor").encoder;
        parallelPod.setDirection(Motor.Direction.REVERSE);
        perpindicularPod = new MotorEx(hardwareMap, "backRightMotor").encoder;
        perpindicularPod.setDirection(Motor.Direction.REVERSE);

        clawSensor = hardwareMap.get(DigitalChannel.class, "clawSensor");


        try {
            voltageSensor = hardwareMap.voltageSensor.iterator().next();
        } catch (Exception e) {
            voltageSensor = null;
        }
    }

    public void loop(Pose drive, SwerveDrivetrain drivetrain) {
        try {
            if (drive != null) {
                drivetrain.set(drive);
            }
            drivetrain.updateModules();
        } catch (Exception ignored) {}
    }

    public void read(SwerveDrivetrain drivetrain) {
        try {
            drivetrain.read();

        } catch (Exception ignored) {}
    }

    public void write(SwerveDrivetrain drivetrain) {
        try {
            drivetrain.write();

        } catch (Exception ignored) {}
    }

    public void reset() {
        try {
            intakeEncoder.reset();
            liftEncoder.reset();
            parallelPod.reset();
            perpindicularPod.reset();
        } catch (Exception e) {}
        imuOffset = imu.getAngularOrientation().firstAngle;
    }

//    public void clearBulkCache() {
//        PhotonCore.CONTROL_HUB.clearBulkCache();
//    }

    public double getAngle() {
        return imuAngle - imuOffset;
    }

    public void startIMUThread(LinearOpMode opMode) {
        if (Global.USING_IMU) {
            imuThread = new Thread(() -> {
                while (!opMode.isStopRequested() && opMode.opModeIsActive()) {
                    synchronized (imuLock) {
                        imuAngle = imu.getAngularOrientation().firstAngle;
                    }
                }
            });
            imuThread.start();
        }
    }

    public void cameraInit() {
        camera.setPipeline(pipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }
}
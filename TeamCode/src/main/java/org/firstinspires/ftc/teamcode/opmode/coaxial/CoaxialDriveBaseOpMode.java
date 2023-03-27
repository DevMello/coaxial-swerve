package org.firstinspires.ftc.teamcode.opmode.coaxial;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.subsystem.CoaxialSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.SwerveSubsystem;
import tk.devmello.swerve.coaxial.impl.CoaxialModule;

public class CoaxialDriveBaseOpMode extends CommandOpMode {

    protected CoaxialSubsystem drive;
    protected GamepadEx gamepadEx1;
    protected RevIMU imu;
    protected double L = 7;
    protected double W = 8;

    CRServo[] crServos = new CRServo[4];    //Steering
    MotorEx[] encoders = new MotorEx[4];    //Encoders to monitor steering
    Motor[] motors = new Motor[4];

    protected CoaxialModule[] modules = new CoaxialModule[4];
    protected CoaxialModule frontLeft;
    protected CoaxialModule frontRight;
    protected CoaxialModule backLeft;
    protected CoaxialModule backRight;

    @Override
    public void initialize() {
        gamepadEx1 = new GamepadEx(gamepad1);
        //add the modules to the array
        modules[0] = frontLeft;
        modules[1] = frontRight;
        modules[2] = backLeft;
        modules[3] = backRight;

        initHardware();
        imu = new RevIMU(hardwareMap);
        imu.init();
        drive = new CoaxialSubsystem(L,W, frontLeft,frontRight,backLeft,backRight);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }
    public void initHardware() {
        String[] crServoNames = new String[] {"BLC", "FLC", "FRC", "BRC"};
        for (int i=0; i<4; i++) crServos[i] = (CRServo)hardwareMap.get(CRServo.class, crServoNames[i]);
        String[] encoderNames = new String[] {"BLE", "FLE", "FRE", "BRE"};
        for (int i=0; i<4; i++) encoders[i] = (MotorEx)hardwareMap.get(DcMotor.class, encoderNames[i]);
        String[] motorNames = new String[] {"BLM", "FLM", "FRM", "BRM"};
        for (int i=0; i<4; i++) motors[i] = (Motor) hardwareMap.get(DcMotor.class, motorNames[i]);
        //create a for loop which will make 4 different CoaxialModule objects with a servo from crServos, a motor from motors, and an encoder from encoders
        for (int i=0; i<4; i++) {
            createModule(crServos[i], motors[i], encoders[i]);
        }
    }
    public void createModule(CRServo servo, Motor motor, MotorEx encoder) {
        //cycle through the array of modules and create a CoaxialModule object with the servo, motor, and encoder
        for (int i=0; i<4; i++) {
            modules[i] = new CoaxialModule((tk.devmello.swerve.hardware.CRServo) servo, motor, encoder, 0.2);
        }
    }
}

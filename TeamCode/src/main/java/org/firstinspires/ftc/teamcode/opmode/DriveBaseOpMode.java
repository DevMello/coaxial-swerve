package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.subsystem.SwerveSubsystem;

public class DriveBaseOpMode extends CommandOpMode {
    CRServo[] crServos = new CRServo[4];    //Steering
    DcMotor[] encoders = new DcMotor[4];    //Encoders to monitor steering
    DcMotor[] motors = new DcMotor[4];
    protected SwerveSubsystem drive;
    protected GamepadEx gamepadEx1;
    protected RevIMU imu;
    @Override
    public void initialize() {
        gamepadEx1 = new GamepadEx(gamepad1);
        initHardware();
        imu = new RevIMU(hardwareMap);
        imu.init();
        drive = new SwerveSubsystem(crServos, encoders, motors);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }

    protected void initHardware() {
        String[] crServoNames = new String[] {"BLC", "FLC", "FRC", "BRC"};
        for (int i=0; i<4; i++) crServos[i] = (CRServo)hardwareMap.get(CRServo.class, crServoNames[i]);
        String[] encoderNames = new String[] {"BLE", "FLE", "FRE", "BRE"};
        for (int i=0; i<4; i++) encoders[i] = (DcMotor)hardwareMap.get(DcMotor.class, encoderNames[i]);
        String[] motorNames = new String[] {"BLM", "FLM", "FRM", "BRM"};
        for (int i=0; i<4; i++) motors[i] = (DcMotor)hardwareMap.get(DcMotor.class, motorNames[i]);
    }

    @Override
    public void run() {
        super.run();
        telemetry.update();
    }
}

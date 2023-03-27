package org.firstinspires.ftc.teamcode.opmode.swerve;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.drive.swerve.DriveRobotCentricSwerve;

@TeleOp(name = "Swerve Drive Test", group = "TeleOp")
public class SwerveDriveOpMode extends SwerveDriveBaseOpMode {
    private DriveRobotCentricSwerve robotCentricDrive;

    @Override
    public void initialize() {
        super.initialize();
        robotCentricDrive = new DriveRobotCentricSwerve(drive, gamepadEx1::getLeftX ,
                gamepadEx1::getLeftY, gamepadEx1::getRightX);
        register(drive);
        drive.setDefaultCommand(robotCentricDrive);
    }
}

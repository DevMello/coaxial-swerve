package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.drive.DriveRobotCentric;

@TeleOp(name = "Swerve Drive Test", group = "TeleOp")
public class DriveOpMode extends DriveBaseOpMode{
    private DriveRobotCentric robotCentricDrive;

    @Override
    public void initialize() {
        super.initialize();
        robotCentricDrive = new DriveRobotCentric(drive, gamepadEx1::getLeftX ,
                gamepadEx1::getLeftY, gamepadEx1::getRightX);
        register(drive);
        drive.setDefaultCommand(robotCentricDrive);
    }
}

package org.firstinspires.ftc.teamcode.opmode.coaxial;


import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.drive.coaxial.DriveFieldCentricCoaxial;
import org.firstinspires.ftc.teamcode.commands.drive.coaxial.DriveRobotCentricCoaxial;
import org.firstinspires.ftc.teamcode.commands.drive.coaxial.ToggleSlowModeCoaxial;

@TeleOp(name = "Coaxial Drive Test", group = "TeleOp")
public class CoaxialDriveOpMode extends CoaxialDriveBaseOpMode {
    private DriveRobotCentricCoaxial robotCentricDrive;
    private DriveFieldCentricCoaxial fieldCentricDrive;
    @Override
    public void initialize() {
        super.initialize();
        robotCentricDrive = new DriveRobotCentricCoaxial(drive, gamepadEx1::getLeftX ,
                gamepadEx1::getLeftY, gamepadEx1::getRightX);
        fieldCentricDrive = new DriveFieldCentricCoaxial(drive, gamepadEx1::getLeftX ,
                gamepadEx1::getLeftY, gamepadEx1::getRightX, imu::getHeading);
        gb1(GamepadKeys.Button.A).toggleWhenPressed(robotCentricDrive,fieldCentricDrive);
        gb1(GamepadKeys.Button.B).whenPressed(new ToggleSlowModeCoaxial(drive));
        register(drive);
        drive.setDefaultCommand(robotCentricDrive);
    }
}

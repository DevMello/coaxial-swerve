package org.firstinspires.ftc.teamcode.commands.drive.coaxial;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.opmode.coaxial.CoaxialDriveBaseOpMode;
import org.firstinspires.ftc.teamcode.subsystem.CoaxialSubsystem;

import java.util.function.DoubleSupplier;

public class DriveFieldCentricSlowModeCoaxial extends CommandBase {
    private final CoaxialSubsystem drive;
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeed, gyroAngle;
    public DriveFieldCentricSlowModeCoaxial(CoaxialSubsystem drive, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier turnSpeed, DoubleSupplier gyroAngle) {
        this.drive = drive;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.turnSpeed = turnSpeed;
        this.gyroAngle = gyroAngle;
        addRequirements(drive);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void execute() {
        drive.driveFieldCentricSlowMode(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), turnSpeed.getAsDouble(),gyroAngle.getAsDouble());
    }
}

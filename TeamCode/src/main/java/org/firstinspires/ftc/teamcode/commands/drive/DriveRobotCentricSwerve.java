package org.firstinspires.ftc.teamcode.commands.drive;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystem.SwerveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveRobotCentricSwerve extends CommandBase {
    private final SwerveSubsystem drive;
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeed;

    public DriveRobotCentricSwerve(SwerveSubsystem drive, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier turnSpeed){
        this.drive = drive;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.turnSpeed = turnSpeed;

        addRequirements(drive);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void execute() {
        drive.driveRobotCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), turnSpeed.getAsDouble());
    }
}


package org.firstinspires.ftc.teamcode.commands.drive.coaxial;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystem.CoaxialSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.SwerveSubsystem;
import tk.devmello.swerve.coaxial.CoaxialDrive;

import java.util.function.DoubleSupplier;

public class DriveRobotCentricCoaxial extends CommandBase {
    private final CoaxialSubsystem drive;
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeed;

    public DriveRobotCentricCoaxial(CoaxialSubsystem drive, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier turnSpeed){
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

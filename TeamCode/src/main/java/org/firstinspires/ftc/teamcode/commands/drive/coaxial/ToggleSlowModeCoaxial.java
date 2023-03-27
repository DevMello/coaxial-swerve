package org.firstinspires.ftc.teamcode.commands.drive.coaxial;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystem.CoaxialSubsystem;

public class ToggleSlowModeCoaxial extends CommandBase {
    protected CoaxialSubsystem drive;
    public ToggleSlowModeCoaxial(CoaxialSubsystem drive) {
        this.drive = drive;
        addRequirements(drive);
    }
    @Override
    public void initialize() {
        drive.toggleSlowMode();
    }
    @Override
    public boolean isFinished() {
        return true;
    }
}

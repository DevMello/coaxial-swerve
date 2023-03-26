package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import tk.devmello.swerve.coaxial.CoaxialDrive;

@Config
public class CoaxialSubsystem extends SubsystemBase{
    private final CoaxialDrive coaxialDrive;
    public static double slowModeFactor = 3;
    public static double slowRotScale = .75;
    //TODO: Refactor Parameters to take in a CoaxialModule array
    public CoaxialSubsystem(CoaxialDrive coaxialDrive) {
        this.coaxialDrive = coaxialDrive;
    }
    public void driveFieldCentric(double strafeSpeed, double forwardSpeed, double turnSpeed, double gyroAngle){
        coaxialDrive.driveFieldCentric(-strafeSpeed, -forwardSpeed, -turnSpeed, gyroAngle);
    }
    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed){
        coaxialDrive.driveRobotCentric(-strafeSpeed, -forwardSpeed, -turnSpeed*slowRotScale);
    }
    public void driveRobotCentricSlowMode(double strafeSpeed, double forwardSpeed, double turnSpeed) {
        coaxialDrive.driveRobotCentric(-strafeSpeed / slowModeFactor,
                -forwardSpeed / slowModeFactor,
                -turnSpeed / slowModeFactor);
    }

}

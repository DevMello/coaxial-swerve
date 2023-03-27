package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.subsystem.options.DriveMode;
import tk.devmello.mellolib.swerve.coaxial.CoaxialDrive;
import tk.devmello.mellolib.swerve.coaxial.impl.CoaxialModule;

@Config
public class CoaxialSubsystem extends SubsystemBase{
    private final CoaxialDrive coaxialDrive;
    public static double slowModeFactor = 3;
    public static double slowRotScale = .75;
    public DriveMode driveMode = DriveMode.NORMAL_MODE;
    public CoaxialSubsystem(double L,double W, CoaxialModule... modules) {
        this.coaxialDrive = new CoaxialDrive(L,W, modules);
    }
    public void driveFieldCentric(double strafeSpeed, double forwardSpeed, double turnSpeed, double gyroAngle){
        switch (driveMode) {
            case NORMAL_MODE:
                coaxialDrive.driveFieldCentric(strafeSpeed, -forwardSpeed, -turnSpeed*slowRotScale, gyroAngle);
                break;
            case SLOW_MODE:
                driveFieldCentricSlowMode(strafeSpeed, -forwardSpeed, -turnSpeed*slowRotScale, gyroAngle);
                break;
        }
    }
    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed){
        switch (driveMode) {
            case NORMAL_MODE:
                coaxialDrive.driveRobotCentric(strafeSpeed, -forwardSpeed, -turnSpeed*slowRotScale);
                break;
            case SLOW_MODE:
                driveRobotCentricSlowMode(strafeSpeed, -forwardSpeed, -turnSpeed*slowRotScale);
                break;
        }
    }
    public void driveRobotCentricSlowMode(double strafeSpeed, double forwardSpeed, double turnSpeed) {
        coaxialDrive.driveRobotCentric(strafeSpeed / slowModeFactor,
                -forwardSpeed / slowModeFactor,
                -turnSpeed / slowModeFactor);
    }
    public void driveFieldCentricSlowMode(double strafeSpeed, double forwardSpeed, double turnSpeed, double gyroAngle) {
        coaxialDrive.driveFieldCentric(strafeSpeed / slowModeFactor,
                -forwardSpeed / slowModeFactor,
                -turnSpeed / slowModeFactor,
                gyroAngle);
    }

    public void toggleSlowMode() {
       if (driveMode == DriveMode.NORMAL_MODE) {
           driveMode = DriveMode.SLOW_MODE;
       } else {
           driveMode = DriveMode.NORMAL_MODE;
       }
    }
}

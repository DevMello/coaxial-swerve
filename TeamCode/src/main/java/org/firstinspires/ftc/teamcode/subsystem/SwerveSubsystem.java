package org.firstinspires.ftc.teamcode.subsystem;


import com.arcrobotics.ftclib.command.SubsystemBase;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import tk.devmello.swerve.coaxial.SwerveDrive;

public class SwerveSubsystem extends SubsystemBase {
    private final SwerveDrive drive;

    public static double slowModeFactor = 3;
    public static double slowRotScale = .75;
    public SwerveSubsystem(CRServo[] crServos, DcMotor[] encoders, DcMotor[] motors){
        drive = new SwerveDrive(crServos, encoders, motors);
    }

    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed){
        drive.setDriveSpeed(strafeSpeed, -forwardSpeed, -turnSpeed*slowRotScale);
    }

    public void driveRobotCentricSlowMode(double strafeSpeed, double forwardSpeed, double turnSpeed){
        drive.setDriveSpeed(strafeSpeed/slowModeFactor, -(forwardSpeed/slowModeFactor), -(turnSpeed/slowModeFactor));
    }
}

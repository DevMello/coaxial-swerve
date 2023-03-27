package tk.devmello.swerve.coaxial;

import tk.devmello.math.geometry.Vector2d;
import tk.devmello.swerve.RobotDrive;
import tk.devmello.swerve.coaxial.impl.CoaxialModule;

public class CoaxialDrive extends RobotDrive{
    private CoaxialModule[] modules;
    /**
     * the vertical distance between each wheel
     */
    private double L;
    /**
     * the horizontal distance between each wheel
     */
    private double W;
    /**
     * The constructor for the swerve drivetrain.
     *
     * @param wheelbase     The value for {@link #L}.
     * @param trackwidth    The value for {@link #W}.
     * @param modules       The modules for the drivetrain. Make sure to put in this order:
     *                      frontLeft, frontRight, backLeft, backRight.
     */
    public CoaxialDrive(double wheelbase, double trackwidth, CoaxialModule... modules) {
        this.modules = modules;
        L = wheelbase;
        W = trackwidth;
    }
    @Override
    public void stopMotor() {
        for (CoaxialModule module : modules) {
            module.stopMotor();
        }
    }

    public void driveRobotCentric(double xSpeed, double ySpeed, double turnSpeed) {
        driveFieldCentric(xSpeed, ySpeed, turnSpeed, 0);
    }
    public void driveFieldCentric(double xSpeed, double ySpeed, double turnSpeed, double gyroAngle) {
        //THIS IS VERY SKETCH AND LITERALLY WAS COPIED FROM AN FRC PAPER. IF THIS WORKS, I WILL BE VERY SURPRISED.
        double A = xSpeed - turnSpeed * L / 2;
        double B = xSpeed + turnSpeed * L / 2;
        double C = ySpeed - turnSpeed * W / 2;
        double D = ySpeed + turnSpeed * W / 2;

        Vector2d v2 = new Vector2d(B, C);
        Vector2d v1 = new Vector2d(B, D);
        Vector2d v3 = new Vector2d(A, D);
        Vector2d v4 = new Vector2d(A, C);
        v1.rotateBy(-gyroAngle);
        v2.rotateBy(-gyroAngle);
        v3.rotateBy(-gyroAngle);
        v4.rotateBy(-gyroAngle);

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[MotorType.kFrontLeft.value] = v1.magnitude();
        wheelSpeeds[MotorType.kFrontRight.value] = v2.magnitude();
        wheelSpeeds[MotorType.kBackLeft.value] = v3.magnitude();
        wheelSpeeds[MotorType.kBackRight.value] = v4.magnitude();

        normalize(wheelSpeeds);

        double[] rotationalSpeeds = new double[4];
        rotationalSpeeds[MotorType.kFrontLeft.value] = v1.angle();
        rotationalSpeeds[MotorType.kFrontRight.value] = v2.angle();
        rotationalSpeeds[MotorType.kBackLeft.value] = v3.angle();
        rotationalSpeeds[MotorType.kBackRight.value] = v4.angle();

        modules[MotorType.kFrontLeft.value]
                .driveModule(wheelSpeeds[MotorType.kFrontLeft.value],
                        rotationalSpeeds[MotorType.kFrontLeft.value]);
        modules[MotorType.kFrontRight.value]
                .driveModule(wheelSpeeds[MotorType.kFrontRight.value],
                        rotationalSpeeds[MotorType.kFrontRight.value]);
        modules[MotorType.kBackLeft.value]
                .driveModule(wheelSpeeds[MotorType.kBackLeft.value],
                        rotationalSpeeds[MotorType.kBackLeft.value]);
        modules[MotorType.kBackRight.value]
                .driveModule(wheelSpeeds[MotorType.kBackRight.value],
                        rotationalSpeeds[MotorType.kBackRight.value]);
    }
}

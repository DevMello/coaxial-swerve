package tk.devmello.mellolib.swerve.coaxial;

import tk.devmello.mellolib.geometry.Vector2d;
import tk.devmello.mellolib.swerve.coaxial.impl.CoaxialModule;
import tk.devmello.mellolib.swerve.RobotDrive;

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

    /**
     * stops all motors
     */
    @Override
    public void stopMotor() {
        for (CoaxialModule module : modules) {
            module.stopMotor();
        }
    }

    public void driveRobotCentric(double xSpeed, double ySpeed, double turnSpeed) {
        driveFieldCentric(xSpeed, ySpeed, turnSpeed, 0);
    }
    /**
     * Drives the robot in a field centric manner.
     *
     * @param xSpeed        The speed in the x direction.
     * @param ySpeed        The speed in the y direction.
     * @param turnSpeed     The speed of the turn.
     * @param gyroAngle     The angle of the gyro.
     */
    public void driveFieldCentric(double xSpeed, double ySpeed, double turnSpeed, double gyroAngle) {
        //THIS IS VERY SKETCH AND LITERALLY WAS COPIED FROM AN FRC PAPER. IF THIS WORKS, I WILL BE VERY SURPRISED.
        double A = xSpeed - turnSpeed * L / 2;
        double B = xSpeed + turnSpeed * L / 2;
        double C = ySpeed - turnSpeed * W / 2;
        double D = ySpeed + turnSpeed * W / 2;

        /**
         * The following code is a bit of a mess, but it works. It takes the inputted
         * speeds and turns them into the speeds for each wheel. It also takes the
         * gyro angle and rotates the inputted speeds by that angle.
         */
        Vector2d v2 = new Vector2d(B, C);
        Vector2d v1 = new Vector2d(B, D);
        Vector2d v3 = new Vector2d(A, D);
        Vector2d v4 = new Vector2d(A, C);
        v1.rotateBy(-gyroAngle);
        v2.rotateBy(-gyroAngle);
        v3.rotateBy(-gyroAngle);
        v4.rotateBy(-gyroAngle);

        /**
         * The following code takes the speeds for each wheel and normalizes them so
         * that the maximum speed is 1. This is so that the robot doesn't go faster
         * when it turns.
         */
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

        /**
         * The following code takes the normalized speeds and sets the speeds of the
         * wheels.
         */
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

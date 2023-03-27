package tk.devmello.mellolib.kinematics;

import tk.devmello.mellolib.kinematics.geometry.Pose2d;
import tk.devmello.mellolib.kinematics.geometry.Vector2d;

public class SwerveKinematics {
    /**
     * Computes the wheel velocity vectors corresponding to [robotVel] given the provided [trackWidth] and
     * [wheelBase].
     *
     * @param robotVel velocity of the robot in its reference frame
     * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
     * @param wheelBase distance between pairs of wheels on the same side of the robot
     */
    public static Vector2d[] computeWheelVelocities(Vector2d robotVel, double trackWidth, double wheelBase) {
        double a = robotVel.x - robotVel.y * (trackWidth / wheelBase);
        double b = robotVel.x + robotVel.y * (trackWidth / wheelBase);
        double c = robotVel.x + robotVel.y * (trackWidth / wheelBase);
        double d = robotVel.x - robotVel.y * (trackWidth / wheelBase);
        return new Vector2d[]{
                new Vector2d(a, b),
                new Vector2d(c, d)
        };
    }

    /**
     * Computes the wheel velocities corresponding to [robotVel] given the provided [trackWidth] and
     * [wheelBase].
     *
     * @param robotVel velocity of the robot in its reference frame
     * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
     * @param wheelBase distance between pairs of wheels on the same side of the robot
     */
    public static double[] computeWheelVelocities(double robotVel, double trackWidth, double wheelBase) {
        double a = robotVel - robotVel * (trackWidth / wheelBase);
        double b = robotVel + robotVel * (trackWidth / wheelBase);
        double c = robotVel + robotVel * (trackWidth / wheelBase);
        double d = robotVel - robotVel * (trackWidth / wheelBase);
        return new double[]{a, b, c, d};
    }
    /**
     * Computes the module orientations (in radians) corresponding to [robotVel] given the provided
     * [trackWidth] and [wheelBase].
     *
     * @param robotVel velocity of the robot in its reference frame
     * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
     * @param wheelBase distance between pairs of wheels on the same side of the robot
     */
    public static double[] computeModuleOrientations(Vector2d robotVel, double trackWidth, double wheelBase) {
        double a = Math.atan2(robotVel.x - robotVel.y * (trackWidth / wheelBase), wheelBase);
        double b = Math.atan2(robotVel.x + robotVel.y * (trackWidth / wheelBase), wheelBase);
        double c = Math.atan2(robotVel.x + robotVel.y * (trackWidth / wheelBase), wheelBase);
        double d = Math.atan2(robotVel.x - robotVel.y * (trackWidth / wheelBase), wheelBase);
        return new double[]{a, b, c, d};
    }
    /**
     * Computes the acceleration vectors corresponding to [robotAccel] given the provided [trackWidth] and
     * [wheelBase].
     *
     * @param robotAccel velocity of the robot in its reference frame
     * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
     * @param wheelBase distance between pairs of wheels on the same side of the robot
     */
    public static Vector2d[] computeWheelAccelerations(Vector2d robotAccel, double trackWidth, double wheelBase) {
        double a = robotAccel.x - robotAccel.y * (trackWidth / wheelBase);
        double b = robotAccel.x + robotAccel.y * (trackWidth / wheelBase);
        double c = robotAccel.x + robotAccel.y * (trackWidth / wheelBase);
        double d = robotAccel.x - robotAccel.y * (trackWidth / wheelBase);
        return new Vector2d[]{
                new Vector2d(a, b),
                new Vector2d(c, d)
        };
    }
    /**
     * Computes the wheel accelerations corresponding to [robotAccel] given the provided [trackWidth] and
     * [wheelBase].
     *
     * @param robotAccel velocity of the robot in its reference frame
     * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
     * @param wheelBase distance between pairs of wheels on the same side of the robot
     */
    public static double[] computeWheelAccelerations(double robotAccel, double trackWidth, double wheelBase) {
        double a = robotAccel - robotAccel * (trackWidth / wheelBase);
        double b = robotAccel + robotAccel * (trackWidth / wheelBase);
        double c = robotAccel + robotAccel * (trackWidth / wheelBase);
        double d = robotAccel - robotAccel * (trackWidth / wheelBase);
        return new double[]{a, b, c, d};
    }

    /**
     * Computes the module angular velocities corresponding to [robotAccel] given the provided [trackWidth]
     * and [wheelBase].
     *
     * @param robotAccel velocity of the robot in its reference frame
     * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
     * @param wheelBase distance between pairs of wheels on the same side of the robot
     */
    public static double[] computeModuleAngularVelocities(Vector2d robotAccel, double trackWidth, double wheelBase) {
        double a = (robotAccel.x - robotAccel.y * (trackWidth / wheelBase)) / wheelBase;
        double b = (robotAccel.x + robotAccel.y * (trackWidth / wheelBase)) / wheelBase;
        double c = (robotAccel.x + robotAccel.y * (trackWidth / wheelBase)) / wheelBase;
        double d = (robotAccel.x - robotAccel.y * (trackWidth / wheelBase)) / wheelBase;
        return new double[]{a, b, c, d};
    }
    /**
     * Computes the robot velocities corresponding to [wheelVelocities], [moduleOrientations], and the drive parameters.
     *
     * @param wheelVelocities wheel velocities (or wheel position deltas)
     * @param moduleOrientations wheel orientations (in radians)
     * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
     * @param wheelBase distance between pairs of wheels on the same side of the robot
     */
    public static Vector2d computeRobotVelocity(double[] wheelVelocities, double[] moduleOrientations, double trackWidth, double wheelBase) {
        double a = wheelVelocities[0] * Math.cos(moduleOrientations[0]);
        double b = wheelVelocities[1] * Math.cos(moduleOrientations[1]);
        double c = wheelVelocities[2] * Math.cos(moduleOrientations[2]);
        double d = wheelVelocities[3] * Math.cos(moduleOrientations[3]);
        double x = (a + b + c + d) / 4;
        double y = (a - b + c - d) / (4 * (trackWidth / wheelBase));
        return new Vector2d(x, y);
    }
    /**
     * Computes the robot velocities corresponding to [wheelVelocities], [moduleOrientations], and the drive parameters.
     *
     * @param wheelVelocities wheel velocities (or wheel position deltas)
     * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
     * @param wheelBase distance between pairs of wheels on the same side of the robot
     */
    public static Pose2d wheelToRobotVelocities(Pose2d wheelVelocities, double trackWidth, double wheelBase) {
        double a = wheelVelocities.x * Math.cos(wheelVelocities.heading);
        double b = wheelVelocities.y * Math.cos(wheelVelocities.heading);
        double c = wheelVelocities.x * Math.sin(wheelVelocities.heading);
        double d = wheelVelocities.y * Math.sin(wheelVelocities.heading);
        double x = (a + b + c + d) / 4;
        double y = (a - b + c - d) / (4 * (trackWidth / wheelBase));
        double heading = (a - b - c + d) / (4 * (trackWidth / wheelBase));
        return new Pose2d(x, y, heading);
    }
}

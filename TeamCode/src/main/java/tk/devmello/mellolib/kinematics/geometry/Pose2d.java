package tk.devmello.mellolib.kinematics.geometry;

import android.annotation.SuppressLint;
import tk.devmello.mellolib.kinematics.util.Angle;

public class Pose2d {
    /**
     * Class for representing 2D robot poses (x, y, and heading) and their derivatives.
     */

    /**
     * Returns the distance between two poses.
     */
    public double x;
    public double y;
    public double heading;

    /**
     *
     * @param x
     * @param y
     * @param heading
     * Constructor for Pose2d class with x, y, and heading
     */
    public Pose2d(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    /**
     *
     * @return
     * Method for returning the x value of the pose
     *
     *
     */
    public Vector2d vec() {
        return new Vector2d(x, y);
    }

    /**
     *
     * @param other
     * @return
     * Method for returning the distance between two poses
     */
    public Pose2d plus(Pose2d other) {
        return new Pose2d(x + other.x, y + other.y, heading + other.heading);
    }
    /**
     *
     * @param other
     * @return
     * Method for returning the difference between two poses
     */
    public Pose2d minus(Pose2d other) {
        return new Pose2d(x - other.x, y - other.y, heading - other.heading);
    }


    /**
     *
     * @param scalar
     * @return
     * Method for returning the product of a pose and a scalar
     */
    public Pose2d times(double scalar) {
        return new Pose2d(x * scalar, y * scalar, heading * scalar);

    }

    /**
     *
     * @param scalar
     * @return
     * Method for returning the quotient of a pose and a scalar
     */
    public Pose2d div(double scalar) {
        return new Pose2d(x / scalar, y / scalar, heading / scalar);
    }

    /**
     *
     * @return
     * Method for returning the negative of a pose
     */
    public Pose2d unaryMinus() {
        return new Pose2d(-x, -y, -heading);
    }
    /**
     *
     * @param other
     * @return
     * Method for returng Epsilon Equals of two poses
     */
    public boolean epsilonEquals(Pose2d other) {
        return x == other.x && y == other.y && heading == other.heading;
    }
    /**
     *
     * @param other
     * @return
     * Method for returning epsilon equals of two poses with heading
     */
    public boolean epsilonEqualsHeading(Pose2d other) {
        return x == other.x && y == other.y && Angle.normDelta(heading - other.heading) == 0.0;
    }

    /**
     *
     * @param
     * @return
     * Method for returning heading vector of a pose
     */
    public Vector2d headingVec() {
        return new Vector2d(Math.cos(heading), Math.sin(heading));
    }



    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getHeading() {
        return heading;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f°)", x, y, Math.toDegrees(heading));
    }
}

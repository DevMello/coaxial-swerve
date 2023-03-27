package tk.devmello.mellolib.kinematics.util;

public class Angle {
    //rewrite the Angle kotlin class into java
    private double value;
    public static double TWO_PI = 2 * Math.PI;
    public static double PI = Math.PI;
    /**
     * Returns [angle] clamped to `[0, 2pi]`.
     *
     * @param angle angle measure in radians
     */
    public static double normalize(double angle) {
        return (angle % TWO_PI + TWO_PI) % TWO_PI;
    }
    /**
     * Returns [angle] clamped to `[-pi, pi]`.
     *
     * @param angle angle measure in radians
     */
    public static double normalizeSigned(double angle) {
        return normalize(angle + PI) - PI;
    }

    public static double normDelta(double angle) {
        double delta = normalize(angle);
        if (delta > PI) {
            delta -= TWO_PI;
        }
        return delta;
    }
}

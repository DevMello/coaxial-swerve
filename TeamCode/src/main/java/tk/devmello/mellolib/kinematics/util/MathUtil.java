package tk.devmello.mellolib.kinematics.util;

public class MathUtil {
    //rewrite the MathUtil kotlin class into java

    public static final double EPSILON = 1e-6;
    /**
     * Returns the real solutions to the quadratic ax^2 + bx + c.
     */
    public static double[] solveQuadratic(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new double[0];
        } else if (discriminant == 0) {
            return new double[]{-b / (2 * a)};
        } else {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            return new double[]{-b / (2 * a) + sqrtDiscriminant / (2 * a), -b / (2 * a) - sqrtDiscriminant / (2 * a)};
        }
    }

    public static boolean epsilonEquals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
}

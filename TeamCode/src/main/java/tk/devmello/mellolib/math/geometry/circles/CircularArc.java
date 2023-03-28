package tk.devmello.mellolib.math.geometry.circles;


import tk.devmello.mellolib.math.geometry.framework.Point;

/**
 * NOTE: Uncommented
 */

public class CircularArc extends Circle {
    private final Point arcSt, arcEnd;
    private final double angle;

    public CircularArc(Point center, Point start, double angle) {
        super(center, center.getDistanceTo(start));
        arcSt = start;
        arcEnd = start.getRotated(center, angle);
        this.angle = angle;
    }

    @Override
    public Point getAt(double t) {
        return arcSt.getRotated(center, t/angle);
    }
}

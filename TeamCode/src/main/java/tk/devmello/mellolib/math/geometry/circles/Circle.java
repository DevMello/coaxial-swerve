package tk.devmello.mellolib.math.geometry.circles;

import tk.devmello.mellolib.math.geometry.framework.GeometryObject;
import tk.devmello.mellolib.math.geometry.framework.Point;
import tk.devmello.mellolib.math.geometry.framework.Tracer;
import tk.devmello.mellolib.math.geometry.position.Vector;


/**
 * NOTE: Uncommented
 */

public class Circle extends GeometryObject implements Tracer {

    protected final Point center;
    protected double r;

    // TOD 5 Fix circle scaling (maybe have endpoint or something)

    public Circle(Point center, double r) {
        this.center = center;
        this.r = r;
        addPoints(center);
    }

    public double getCenterX(){ return center.getX(); }
    public double getCenterY(){ return center.getY(); }
    public Point getCenter() { return center; }
    public double getRadius(){ return r; }

    @Override
    public Point getAt(double t) {
        return center.getRotated(t/360.0);
    }

    public Point getClosestTo(Point p){ return new Vector(center, p).getUnitVector().getScaled(r).getPoint().getAdded(center); }

    public Circle getScaledRadius(double scale){
        return new Circle(this.center.getCopy(), r*scale);
    }

    @Override
    public Circle getCopy() {
        return new Circle(center.getCopy(), r);
    }
}

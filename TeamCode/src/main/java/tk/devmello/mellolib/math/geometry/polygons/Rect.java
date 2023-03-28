package tk.devmello.mellolib.math.geometry.polygons;

import tk.devmello.mellolib.math.geometry.framework.Point;
import tk.devmello.mellolib.math.geometry.position.Vector;

/**
 * NOTE: Uncommented
 */

public class Rect extends Quadrilateral {
    private final Point center;
    private double angle = 0;

    public Rect(Point bl, Point tr){
        double x1 = Math.min(bl.getX(), tr.getX());
        double x2 = Math.max(bl.getX(), tr.getX());
        double y1 = Math.min(bl.getY(), tr.getY());
        double y2 = Math.max(bl.getY(), tr.getY());
        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x1, y2);
        this.p3 = new Point(x2, y2);
        this.p4 = new Point(x2, y1);
        center = new Point((x1+x2)/2.0, (y1+y2)/2.0);
        addPoints(p1, p2, p3, p4);
    }

    public Rect(Point center, double width, double height){
       this.p1 = center.getTranslated(new Vector(width, height).getScaled(0.5));
       Vector vector = new Vector(center, p1);
       this.p2 = p1.getTranslated(vector.getReflectedX());
       this.p3 = p1.getTranslated(vector.getInverted());
       this.p4 = p1.getTranslated(vector.getReflectedY());
       this.center = center;
       addPoints(this.p1, p2, p3, p4);
    }

    public Rect(Point center, double width, double height, double angle){
        this(center, width, height);
        this.rotate(center, angle);
        this.angle = angle;
    }

    public Point getCenter(){ return center; }
    public double getAngle(){ return angle; }

    @Override
    public String toString() {
        return "Rect {" + "center=" + center + ", p1=" + p1 + ", angle=" + angle + '}';
    }
}
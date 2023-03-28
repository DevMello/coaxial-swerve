package tk.devmello.mellolib.math.geometry.position;

import tk.devmello.mellolib.math.geometry.framework.GeometryObject;
import tk.devmello.mellolib.math.geometry.framework.Point;
import tk.devmello.mellolib.util.Iterator;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * NOTE: Uncommented
 */

public class Pose extends GeometryObject {
    private final Point p;
    private double angle; // Units same as input units
    public Pose(Point p, double angle) { this.p = p; setAngle(angle); addPoints(this.p); }
    public Pose(Vector v, double angle) { this.p = v.getPoint(); setAngle(angle); addPoints(p);}
    public Pose(double x, double y, double angle) { this.p = new Point(x,y); setAngle(angle); addPoints(p);}
    public Pose(double[] pose){this.p = new Point(pose[0], pose[1]); setAngle(pose[2]); addPoints(p);}
    public Pose(){ this.p = new Point(); setAngle(0); addPoints(p);}

    public void add(Pose in){p.translate(in.getX(), in.getY()); angle += in.getAngle(); }
    public void add(Vector in){p.translate(in.getX(), in.getY());}
    public Pose getAdded(Pose in){return new Pose(p.getTranslated(in.getX(), in.getY()), this.getAngle()+in.getAngle());}
    public Pose getInverted(){ return new Pose(-getX(), -getY(), -getAngle()); }
    public Pose getSubtracted(Pose in){ return getAdded(in.getInverted()); }

    public double getX(){ return p.getX(); }
    public double getY(){ return p.getY(); }
    public Point getPoint(){ return p; }
    public Vector getVector(){ return new Vector(p); }
    public Vector getAngleUnitVector(){ return new Vector(getAngle()); }
    public double getAngle(){ return angle; }
    public Pose getOnlyPointRotated(double angle){ return new Pose(p.getRotated(angle), getAngle()); }
    public Pose getOnlyOrientationRotated(double angle){ return new Pose(p, getAngle()+angle); }
    public void setX(double x){ p.setX(x);}
    public void setY(double y){ p.setY(y);}
    public void setVector(Vector in){ setX(in.getX()); setY(in.getY()); }
    public void setAngle(double angle){ this.angle = angle; }
    public void setZero(){ setX(0); setY(0); setAngle(0); }
    public void invertOrientation(){ setAngle(-getAngle()); }
    public void invertX(){ setX(-getX());}
    public void invertY(){ setY(-getY());}
    public void rotateOrientation(double angle){ setAngle(getAngle() + angle);}
    public void scaleOrientation(double scale){setAngle(getAngle()*scale);}
    public Pose getCopy(){ return new Pose(getX(), getY(), getAngle()); }
    public Pose getOrientationInverted(){ return new Pose(p.getCopy(), -getAngle()); }

    public double getDistanceTo(Pose p){ return getPoint().getDistanceTo(p.getPoint()); }
    public double getAngleTo(Pose p){ return p.getAngle() - getAngle(); }
    public double getLength(){ return getVector().getLength(); }
    public boolean within(Pose target, double disError, double angleError){ return this.getDistanceTo(target) < disError && Math.abs(target.getAngleTo(target)) < angleError; }
    public boolean withinY(Pose target, double yError, double angleError){ return Math.abs(this.getY() - target.getY()) < yError && Math.abs(target.getAngleTo(target)) < angleError; }


    public static Pose forAllAverage(ArrayList<Pose> poses){
        double x = Iterator.forAllAverage(poses, Pose::getX);
        double y = Iterator.forAllAverage(poses, Pose::getY);
        double h = Iterator.forAllAverage(poses, Pose::getAngle);
        return new Pose(x, y, h);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pose pose = (Pose) o;
        return Math.abs(pose.angle-angle) < 0.0001 && pose.p.equals(p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, angle);
    }

    @Override
    public void rotate(Point anchor, double angle) { super.rotate(anchor, angle); rotateOrientation(angle);}

    @Override
    public String toString() { return String.format(Locale.US, "Pose {p: %s, angle: %.3f}", p.toString(), getAngle()); }
}
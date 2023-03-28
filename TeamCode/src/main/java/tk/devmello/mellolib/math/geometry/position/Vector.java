package tk.devmello.mellolib.math.geometry.position;

import tk.devmello.mellolib.codeseg.ParameterCodeSeg;
import tk.devmello.mellolib.math.geometry.framework.GeometryObject;
import tk.devmello.mellolib.math.geometry.framework.Point;
import tk.devmello.mellolib.math.linearalgebra.Vector3D;

import java.util.Locale;

import static java.lang.Math.atan2;

public class Vector extends GeometryObject {
    private final Point p;
    private double theta; // Always in radians (effectively final)

    public Vector(){
        this(0,0);
    }

    public Vector(double x, double y){
        p = new Point(x, y); setTheta();
        addPoints(p);
    }

    public Vector(Point start, Point end){
        p = end.getSubtracted(start); setTheta();
        addPoints(p);
    }

    public Vector(double angle){
        this(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)));
    }

    public Vector(Point end){ this(new Point(), end); }

    public double getX() {
        return p.getX();
    }
    public double getY() {
        return p.getY();
    }
    public Point getPoint(){ return p; }
    public double getLength() {
        return p.getDistanceToOrigin();
    }
    public double getTheta() { return Math.toDegrees(theta); }
    private void setTheta(){ theta = atan2(getY(), getX()); }

    public Vector getCopy(){
        return new Vector(getX(), getY());
    }
    public Vector getCopy(ParameterCodeSeg<Vector> operation){ Vector copy = getCopy(); operation.run(copy); return copy; }

    public void add(Vector v2){ translate(v2.getPoint().getX(), v2.getPoint().getY() ); setTheta(); }
    public void subtract(Vector v2){ translate(-v2.getPoint().getX(), -v2.getPoint().getY() ); setTheta(); }
    public void invert(){ scale(-1); setTheta(); }
    public void reflectX(){ p.reflectX(); setTheta();}
    public void reflectY(){ p.reflectY(); setTheta(); }
    public void scaleToLength(double length){ if(getLength() != 0) { scale(length/getLength());} }
    public void limitLength(double length){ if(getLength() > length){ scaleToLength(length); }}

    @Override
    public void rotate(Point anchor, double angle) { super.rotate(anchor, angle); setTheta(); }

    public Vector getAdded(Vector v2){ return getCopy(v -> v.add(v2)); }
    public Vector getSubtracted(Vector v2){ return getCopy(v -> v.subtract(v2)); }
    public Vector getRotated(double phi){ return getCopy(v -> v.rotate(phi)); }
    public Vector getScaled(double scale){ return getCopy(v -> v.scale(scale)); }
    public Vector getInverted(){ return getCopy(Vector::invert); }
    public Vector getReflectedX(){ return getCopy(Vector::reflectX); }
    public Vector getReflectedY(){ return getCopy(Vector::reflectY); }

    public double getCrossProduct(Vector in){ return Vector3D.getCrossProduct(this, in); }
    public double getDotProduct(Vector in){ return Vector3D.getDotProduct(this, in); }

    public Vector getUnitVector(){ return getLength() == 0.0 ? this : getScaled(1.0/getLength()); }

    public boolean isNonZero(){ return !this.equals(new Vector()); }

    public static Vector xHat(){ return new Vector(1,0); }
    public static Vector yHat(){ return new Vector(0,1); }

    public String toString() { return String.format(Locale.US,"Vector {x: %f, y: %f}", getX(), getY()); }
}

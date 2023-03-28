package tk.devmello.mellolib.math.linearalgebra;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import tk.devmello.mellolib.math.geometry.position.Vector;

public class Vector3D {
    private final VectorF vector;
    public Vector3D(double x, double y, double z){
        vector = new VectorF((float) x, (float) y, (float) z);
    }
    public Vector3D(){ this(0,0,0); }

    public Vector3D(Vector v2d, double z){
        this(v2d.getX(), v2d.getY(), z);
    }

    public double getX(){ return vector.get(0); }
    public double getY(){ return vector.get(1); }
    public double getZ(){ return vector.get(2); }


    public Vector get2D(){ return new Vector(getX(), getY()); }

    public Vector3D getScaled(double scale){ return new Vector3D(getX()*scale, getY()*scale, getZ()*scale); }


    public Vector3D getCrossProduct(Vector3D in){
        return new Vector3D((getY()*in.getZ()) - (getZ()*in.getY()), (getZ()*in.getX()) - (getX()*in.getZ()), (getX()*in.getY()) - (getY()*in.getX()));
    }

    public static double getCrossProduct(Vector in1, Vector in2){
        return (in1.getX()*in2.getY()) - (in1.getY()*in2.getX());
    }

    public static double getDotProduct(Vector in1, Vector in2){
        return (in1.getX()*in2.getX()) + (in1.getY()*in2.getY());
    }

    @Override
    public String toString() {
        return "Vector3D{" + getX() + ", " + getY() + ", " + getZ() + '}';
    }
}

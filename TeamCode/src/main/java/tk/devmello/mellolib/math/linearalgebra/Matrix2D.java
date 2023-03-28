package tk.devmello.mellolib.math.linearalgebra;

import org.firstinspires.ftc.robotcore.external.matrices.GeneralMatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import tk.devmello.mellolib.math.geometry.framework.Point;
import tk.devmello.mellolib.math.geometry.position.Vector;


import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Matrix2D {

    // TOD5 NEW
    // Make more linear algebra classes

    // | a b |
    // | c d |

    // | tl tr |
    // | bl br |

    private final GeneralMatrixF matrix;

    public Matrix2D(
            double tl, double tr,
            double bl, double br
    ){
        matrix = new GeneralMatrixF(2,2, new float[]{(float) tl, (float) tr, (float) bl, (float) br});
    }

    public Vector multiply(Vector vector){
        VectorF out = matrix.multiplied(new VectorF(new float[]{(float) vector.getX(), (float) vector.getY()}));
        return new Vector(out.get(0), out.get(1));
    }

    public Point multiply(Point point){
        VectorF out = matrix.multiplied(new VectorF(new float[]{(float) point.getX(), (float) point.getY()}));
        return new Point(out.get(0), out.get(1));
    }

    public Matrix2D getInverted(){
        MatrixF mat = matrix.inverted();
        return new Matrix2D(mat.get(0,0), mat.get(0,1), mat.get(1,0), mat.get(1,1));
    }

    public Matrix2D getMultiplied(double scalar){
        return new Matrix2D(matrix.get(0,0)*scalar, matrix.get(0,1)*scalar, matrix.get(1,0)*scalar, matrix.get(1,1)*scalar);
    }

    public static Vector solve(Matrix2D matrix, Vector out){
        return matrix.getInverted().multiply(out);
    }

    public static Matrix2D getScaleMatrix(double scale){
        return new Matrix2D(scale, 0, 0, scale);
    }

    public static Matrix2D getRotationMatrix(double angle){
        return new Matrix2D(cos(angle), -sin(angle), sin(angle), cos(angle));
    }

    public static Matrix2D getIntegratedRotationMatrix(double angle){
        return new Matrix2D(sin(angle), cos(angle), -cos(angle), sin(angle));
    }

    public static Matrix2D getIntegratedFromZeroRotationMatrix(double angle){
        return getIntegratedRotationMatrix(angle).getSubtracted(getIntegratedRotationMatrix(0.0));
    }

    public Matrix2D getSubtracted(Matrix2D in){
        matrix.subtract(in.matrix);
        return new Matrix2D(matrix.get(0,0), matrix.get(0,1), matrix.get(1,0), matrix.get(1,1));
    }

    @Override
    public String toString() {
        return "Matrix2D{"
                + matrix.get(0,0) + ", "
                +  matrix.get(0,1) + ", \n         "
                + matrix.get(1,0) + ", "
                + matrix.get(1,1) + '}';
    }
}
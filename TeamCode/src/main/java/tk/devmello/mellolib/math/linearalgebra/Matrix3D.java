package tk.devmello.mellolib.math.linearalgebra;

import org.firstinspires.ftc.robotcore.external.matrices.GeneralMatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

public class Matrix3D {

    private final GeneralMatrixF matrix;

    public Matrix3D(
            double tl, double tm, double tr,
            double ml, double mm, double mr,
            double bl, double bm, double br
    ){
        matrix = new GeneralMatrixF(3,3, new float[]{(float) tl, (float) tm, (float) tr, (float) ml,
                (float) mm, (float) mr, (float) bl, (float) bm, (float) br});
    }

    public Vector3D multiply(Vector3D vector){
        VectorF out = matrix.multiplied(new VectorF(new float[]{(float) vector.getX(), (float) vector.getY(), (float) vector.getZ()}));
        return new Vector3D(out.get(0), out.get(1), out.get(2));
    }

    public Matrix3D getInverted(){
        MatrixF mat = matrix.inverted();
        return new Matrix3D(mat.get(0,0), mat.get(0,1), mat.get(0,2),
                             mat.get(1,0), mat.get(1,1), mat.get(1,2),
                             mat.get(2,0), mat.get(2,1), mat.get(2,2)
        );
    }

    public static Vector3D solve(Matrix3D matrix, Vector3D out){
        return matrix.getInverted().multiply(out);
    }
}

package tk.devmello.mellolib.math.geometry.framework;



import tk.devmello.mellolib.codeseg.ParameterCodeSeg;
import tk.devmello.mellolib.util.Iterator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * NOTE: Uncommented
 */

public abstract class GeometryObject {

    protected Point anchorPoint = new Point();
    protected final ArrayList<Point> points = new ArrayList<>();

    protected final void addPoints(Point... points){ Collections.addAll(this.points, points); }
    protected final void addPoints(ArrayList<Point> points){ this.points.addAll(points); }
    public final void setAnchorPoint(Point point){ this.anchorPoint = point; }

    private void toAllPoints(ParameterCodeSeg<Point> code){ Iterator.forAll(points, code);}

    public final void translate(double deltaX, double deltaY){ toAllPoints(p -> p.translate(deltaX, deltaY)); }
    public void rotate(Point anchor, double angle){ toAllPoints(p -> p.rotate(anchor, angle));}
    public final void rotate(double angle){ rotate(anchorPoint, angle);}
    public final void scale(Point anchor, double scale){ toAllPoints(p -> p.scale(anchor, scale));}
    public final void scale(double scale){ scale(anchorPoint, scale); }
    public final void scaleX(double scale){ toAllPoints(p -> p.scaleX(scale));}
    public final void scaleY(double scale){ toAllPoints(p -> p.scaleY(scale));}

    public abstract GeometryObject getCopy();

    // TOD 5 Make copy methods

}

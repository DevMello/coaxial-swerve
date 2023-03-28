package tk.devmello.mellolib.math.geometry.polygons;

import tk.devmello.mellolib.math.geometry.framework.Point;

/**
 * NOTE: Uncommented
 */

public class Quadrilateral extends Polygon {
    protected Point p1, p2, p3, p4; // effectively final
    public Quadrilateral(Point p1, Point p2, Point p3, Point p4){
        this.p1 = p1; this.p2 = p2; this.p3 = p3; this.p4 = p4;
        addPoints(p1, p2, p3, p4);
    }
    public Quadrilateral(){}

    public Quadrilateral getCopy(){
        return new Quadrilateral(p1.getCopy(), p2.getCopy(), p3.getCopy(), p4.getCopy());
    }
    @Override
    public String toString() {
        return "Quad {" + "p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + ", p4=" + p4 + '}';
    }
}

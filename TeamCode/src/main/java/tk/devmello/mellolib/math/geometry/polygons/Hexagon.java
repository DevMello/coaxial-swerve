package tk.devmello.mellolib.math.geometry.polygons;


import tk.devmello.mellolib.math.geometry.framework.Point;
import tk.devmello.mellolib.math.geometry.position.Vector;

public class Hexagon extends Polygon{
    protected final Point p1, p2, p3, p4,p5, p6;
    public Hexagon(Point p1, Point p2, Point p3, Point p4, Point p5, Point p6){
        this.p1 = p1; this.p2 = p2; this.p3 = p3; this.p4 = p4; this.p5 = p5; this.p6 = p6;
        addPoints(p1, p2, p3, p4,p5,p6);
    }

    public Hexagon(Point center, Point p1) {
        this.p1 = p1;
        Vector vector = new Vector(center, p1);
        this.p2 = center.getTranslated(vector.getRotated(60));
        this.p3 = center.getTranslated(vector.getRotated(120));
        this.p4 = center.getTranslated(vector.getRotated(180));
        this.p5 = center.getTranslated(vector.getRotated(240));
        this.p6 = center.getTranslated(vector.getRotated(300));
    }

    @Override
    public String toString() {
        return "Hexagon {" + "p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + ", p4=" + p4 + ", p5=" + p5 + ", p6=" + p6 + '}';
    }

    @Override
    public Hexagon getCopy() {
        return new Hexagon(p1.getCopy(), p2.getCopy(), p3.getCopy(), p4.getCopy(), p5.getCopy(), p6.getCopy());
    }
}
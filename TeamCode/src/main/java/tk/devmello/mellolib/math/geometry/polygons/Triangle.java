package tk.devmello.mellolib.math.geometry.polygons;


import tk.devmello.mellolib.math.geometry.framework.Point;

public class Triangle extends Polygon {
    private final Point p1, p2, p3;

    public Triangle(Point p1, Point p2, Point p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        addPoints(p1, p2, p3);
    }

    public Point getP1() { return p1; }
    public Point getP2() { return p2; }
    public Point getP3() { return p3; }

    @Override
    public String toString() {
        return "Triangle {" + "p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + '}';
    }

    @Override
    public Triangle getCopy() {
        return new Triangle(p1.getCopy(), p2.getCopy(), p3.getCopy());
    }
}

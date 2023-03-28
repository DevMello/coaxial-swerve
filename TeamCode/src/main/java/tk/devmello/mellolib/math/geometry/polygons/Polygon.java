package tk.devmello.mellolib.math.geometry.polygons;


import tk.devmello.mellolib.math.geometry.framework.GeometryObject;
import tk.devmello.mellolib.math.geometry.framework.Point;
import tk.devmello.mellolib.math.geometry.position.Line;
import tk.devmello.mellolib.util.Iterator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * NOTE: Uncommented
 */

public abstract class Polygon extends GeometryObject {


    public ArrayList<Line> getLines(){
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 1; i < points.size(); i++){
            lines.add(new Line(points.get(i-1), points.get(i)));
        }
        lines.add(new Line(points.get(points.size()-1), points.get(0)));
        return lines;
    }


    public final double getArea(){
        double area = 0.0;
        int j = points.size() - 1;
        for (int i = 0; i < points.size(); i++){
            area += (points.get(j).getX()+points.get(i).getX())*(points.get(j).getY()-points.get(i).getY()); j=i;
        }
        return Math.abs(area/2.0);
    }


    public final Rect getBoundingBox() {
        ArrayList<Double> xs = new ArrayList<>();
        ArrayList<Double> ys = new ArrayList<>();
        Iterator.forAll(points, p -> xs.add(p.getX()));
        Iterator.forAll(points, p -> ys.add(p.getY()));
        return new Rect(new Point(Collections.min(xs), Collections.min(ys)), new Point(Collections.max(xs), Collections.max(ys)));
    }



}

package tk.devmello.mellolib.math.geometry.polygons;

import tk.devmello.mellolib.math.geometry.framework.Point;
import tk.devmello.mellolib.math.geometry.position.Line;
import tk.devmello.mellolib.math.geometry.position.Pose;
import tk.devmello.mellolib.util.Iterator;

import java.util.ArrayList;

public class PolyLine extends Polygon {
    boolean connected = false;

    public PolyLine(Point... points){
        addPoints(points);
    }

    public PolyLine(ArrayList<Point> points){ addPoints(points); }
    public PolyLine(ArrayList<Pose> poses, boolean connected){
        Iterator.forAll(poses, pose -> addPoints(pose.getPoint()));
        this.connected = connected;
    }

    @Override
    public ArrayList<Line> getLines() {
        if(connected) {
            return super.getLines();
        }else{
            ArrayList<Line> lines = new ArrayList<>(); for (int i = 1; i < points.size(); i++){ lines.add(new Line(points.get(i-1), points.get(i))); }
            return lines;
        }
    }

    @Override
    public PolyLine getCopy() {
        ArrayList<Point> pointsCopy = new ArrayList<>();
        Iterator.forAll(points, p -> pointsCopy.add(p.getCopy()));
        return new PolyLine(pointsCopy);
    }
}

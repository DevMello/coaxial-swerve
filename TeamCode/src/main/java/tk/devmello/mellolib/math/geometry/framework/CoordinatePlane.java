package tk.devmello.mellolib.math.geometry.framework;

import tk.devmello.mellolib.math.geometry.circles.Circle;
import tk.devmello.mellolib.math.geometry.polygons.Polygon;
import tk.devmello.mellolib.math.geometry.position.Line;
import tk.devmello.mellolib.math.geometry.position.Pose;
import tk.devmello.mellolib.codeseg.ParameterCodeSeg;
import tk.devmello.mellolib.util.Iterator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * NOTE: Uncommented
 */

public class CoordinatePlane {


    private final ArrayList<GeometryObject> objects = new ArrayList<>();
    public static final Point origin = new Point();

    public CoordinatePlane(GeometryObject... o){ add(o); }

    public void add(GeometryObject... o) { Collections.addAll(objects, o); }
    public void addAll(ArrayList<? extends GeometryObject> objects){ Iterator.forAll(objects, this.objects::add); }

    private void toAllObjects(ParameterCodeSeg<GeometryObject> code){ Iterator.forAll(objects, code);}

    public void translate(double deltaX, double deltaY){ toAllObjects(o -> o.translate(deltaX, deltaY)); }
    public void rotate(Point anchor, double angle){ toAllObjects(o -> o.rotate(anchor,angle)); }
    public void rotate(double angle){ rotate(origin, angle); }
    public void scale(Point anchor, double angle){ Iterator.forAll(objects, o -> o.scale(anchor,angle)); }
    public void scale(double scale){ scale(origin, scale); }
    public void scaleX(double scale){ Iterator.forAll(objects, o -> o.scaleX(scale));}
    public void scaleY(double scale){ Iterator.forAll(objects, o -> o.scaleY(scale));}
    public void reflectX(){ scaleX(-1); }
    public void reflectY(){ scaleY(-1); }
    public void reflectPoses(){ toPoses(Pose::invertOrientation); }
    public void toPoses(ParameterCodeSeg<Pose> code){Iterator.forAll(getPoses(), code); }
    public void setStartInverse(Pose start){ translate(-start.getX(), -start.getY()); rotate(-start.getAngle()); }
    public void setStart(Pose start){ rotate(start.getAngle()); translate(start.getX(), start.getY());}

    public ArrayList<Line> getLines() { return getObjectsOfType(Line.class); }
    public ArrayList<Pose> getPoses() { return getObjectsOfType(Pose.class); }
    public ArrayList<Circle> getCircles(){ return getObjectsOfType(Circle.class); }
    public ArrayList<? extends Polygon> getPolygons(){ return getObjectsOfExtendedType(Polygon.class); }
    public <T extends GeometryObject> ArrayList<T> getObjectsOfType(Class<T> type) { return Iterator.forAllOfType(objects, type); }
    public <T extends GeometryObject> ArrayList<? extends T> getObjectsOfExtendedType(Class<T> type) { return Iterator.forAllOfExtendedType(objects, type); }

    public ArrayList<Pose> getCopyOfPoses(){ ArrayList<Pose> out = new ArrayList<>(); Iterator.forAll(getPoses(), pose -> out.add(pose.getCopy())); return out; }

    public void remove(GeometryObject o){ objects.remove(o); }

    public ArrayList<GeometryObject> getAll(){ return objects; }

    public void removeRedundantObjects(){
        for (int i = 0; i < objects.size()-1; i++) {
            if(objects.get(i).equals(objects.get(i+1))){
                objects.remove(i);
                i--;
            }
        }
    }

    public void removeRedundantPosesEqualTo(Pose pose){
        boolean first = true;
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) instanceof Pose) {
                if (((Pose) objects.get(i)).getDistanceTo(pose) < 1.0) {
                    if(!first) {
                        objects.remove(i);
                        i--;
                    }else{
                        first = false;
                    }
                }
            }
        }
    }

    public CoordinatePlane getCopy(){
        CoordinatePlane copy = new CoordinatePlane();
        Iterator.forAll(objects, o -> copy.add(o.getCopy()));
        return copy;
    }

    public static Pose applyCoordinateTransform(Pose p, ParameterCodeSeg<CoordinatePlane> operation){
        CoordinatePlane plane = new CoordinatePlane(p.getCopy()); operation.run(plane);
        return plane.getPoses().get(0).getCopy();
    }
}
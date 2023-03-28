package tk.devmello.mellolib.math.calc;

import tk.devmello.mellolib.math.Operator;
import tk.devmello.mellolib.math.geometry.framework.Point;

// TOD 5 NEW Finish math classes
public class Differentiator extends Operator {
    private double derivative;
    public void differentiate(double t, double step){
        derivative = (function.f(t+step)-function.f(t-step))/(2*step);
    }
    public void differentiate(Point f1, Point f2){
        derivative = (f2.getY()-f1.getY())/(f2.getX()-f1.getX());
    }
    public void differentiate(Point f1, Point f2, Point f3){
        derivative = (f3.getY()-f1.getY())/(f3.getX()-f1.getX());
    }
    public double getDerivative(){return derivative;}
}

package tk.devmello.mellolib.math.calc;


import tk.devmello.mellolib.math.Operator;
import tk.devmello.mellolib.math.geometry.framework.Point;

public class Integrator extends Operator {
    private double integral;
    public void integrate(double t, double step){
        integral += (step/6.0)*(function.f(t) + (4*function.f(t + (step/2))) + function.f(t + step));
    }
    public void integrate(Point f1, Point f2){
        integral += ((f2.getX() - f1.getX())/2)*(f1.getY() + f2.getY());
    }
    public void integrate(Point f1, Point f2, Point f3){
        integral += (((f3.getX()-f1.getX())/6.0)*(f1.getY()+ (4*f2.getY()) + f3.getY()));
    }
    public double getIntegral(){
        return integral;
    }
    public void reset(){
        integral = 0;
    }
}

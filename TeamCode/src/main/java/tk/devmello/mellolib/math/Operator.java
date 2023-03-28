package tk.devmello.mellolib.math;

import tk.devmello.mellolib.codeseg.ReturnCodeSeg;

public class Operator {
    protected Function function;

    public void defineFunction(Function f){
        this.function = f;
    }
    public void defineFunction(ReturnCodeSeg<Double> returnCodeSeg){
        this.function = new Function() {
            @Override
            public double f(double x) {
                return returnCodeSeg.run();
            }
        };
    }
}

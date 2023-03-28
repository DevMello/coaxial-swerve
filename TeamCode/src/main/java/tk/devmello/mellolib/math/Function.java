package tk.devmello.mellolib.math;


public abstract class Function {
    /**
     * Represents the output of a function to an input or f(x)
     * @param x
     * @return f(x)
     */
    public abstract double f(double x);

    /**
     * Represents the derivative of the function at x
     * NOTE: This method should only be called after it has been overriden
     * @param x
     * @return f'(x)
     */
    public double fprime(double x){return 0;}

    /**
     * Represents the even version of this function (i.e. f(x) = f(-x))
     * NOTE: f(0) will be automatically set to 0
     * @param x
     * @return even(f)
     */
    public double feven(double x){return Math.abs(Math.signum(x))*f(Math.abs(x));}

    /**
     * Represents the odd version of this function (i.e. f(x) = -f(-x))
     * NOTE: f(0) will be automatically set to 0
     * @param x
     * @return odd(f)
     */
    public double fodd(double x){return Math.signum(x)*f(Math.abs(x));}

    /**
     * Represents the y intercept of the function or equivalently f(0)
     * @return f(0)
     */
    public double yint(){return f(0);}


}

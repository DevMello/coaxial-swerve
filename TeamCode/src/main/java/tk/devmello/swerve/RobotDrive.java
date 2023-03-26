package tk.devmello.swerve;

public abstract class RobotDrive {

    public static final double kDefaultRangeMin = -1.0;
    public static final double kDefaultRangeMax = 1.0;
    public static final double kDefaultMaxSpeed = 1.0;
    protected double rangeMin = kDefaultRangeMin;
    protected double rangeMax = kDefaultRangeMax;
    protected double maxOutput = kDefaultMaxSpeed;

    public enum MotorType {
        kBackLeft(2),
        kBackRight(3),
        kFrontLeft(0),
        kFrontRight(1),
        kLeft(0),
        kRight(1),
        kSlide(2);

        public final int value;

        MotorType(int value) {
            this.value = value;
        }
    }

    public RobotDrive() {
    }
    public void setMaxSpeed(double maxOutput) {
        this.maxOutput = maxOutput;
    }
    public void setRange(double min, double max) {
        rangeMin = min;
        rangeMax = max;
    }


    public double clipRange(double value) {
        return value <= rangeMin ? rangeMin
                : Math.min(value, rangeMax);
    }

    public abstract void stopMotor();
    protected void normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }


    protected double squareInput(double input) {
        return input * Math.abs(input);
    }

}
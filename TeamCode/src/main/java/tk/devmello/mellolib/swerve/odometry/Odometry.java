package tk.devmello.mellolib.swerve.odometry;

import tk.devmello.robot.hardware.RobotPart;

public abstract class Odometry extends RobotPart {
    @Override
    public void init() {

    }

    @Override
    public void halt() {

    }

    protected abstract void createEncoders();
    protected abstract void setEncoderPoses();
    protected abstract void setConstantObjects();
    protected abstract void update();
    protected void resetObjects(){}
}

package tk.devmello.robot.hardware;

import tk.devmello.robot.stages.StageBuilder;

public abstract class RobotPart extends StageBuilder {
    //create a robot part class

    /**
     * Represents a part of the robot like the drivetrain or the intake
     * When making a new part of the robot part make sure to extend this class
     * then override the init method
     */
    //init the robot part
    public abstract void init();

    //update the robot part
    public abstract void update();

    //stop the robot part


    //halt the robot part
    public abstract void halt();

    //move method for robot parts

    public abstract void move(double power);



}

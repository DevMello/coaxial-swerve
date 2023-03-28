package tk.devmello.robot.bot;

import tk.devmello.robot.hardware.subsystems.Drive;

public interface RobotUser {
    /**
     * Implement this in any class that needs to use the robot
     */

    /**
     * USED
     */

    Drive drive = new Drive();
}

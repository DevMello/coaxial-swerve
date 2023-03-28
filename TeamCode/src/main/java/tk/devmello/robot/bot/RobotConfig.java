package tk.devmello.robot.bot;

import tk.devmello.mellolib.util.Iterator;
import tk.devmello.robot.hardware.RobotPart;

import java.util.ArrayList;

public class RobotConfig {
    /**
     * Arraylist for parts
     */
    private final ArrayList<RobotPart> parts;


    /**
     * Config Constructor
     * @param parts
     */
    public RobotConfig(RobotPart... parts) {
        this.parts = new ArrayList<>(java.util.Arrays.asList(parts));
    }


    /**
     * Method to to set to current config
     */

    private void instantiate() {
        Iterator.forAll(parts, RobotPart::instantiate);
    }

    /**
     * Method to setConfig, must be called once
     * @param config
     */
    public static void setConfig(RobotConfig config) {
        config.instantiate();
    }
}

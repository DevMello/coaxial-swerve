package tk.devmello.robot.bot;

import static tk.devmello.robot.bot.RobotConfig.setConfig;

public class Configs implements RobotUser{

    /**
     * Used to test only the test part
     */

    RobotConfig SwerveDrive = new RobotConfig(drive);

    /**
     * Current Config
     */
    public void setCurrentConfig(){
        setConfig(SwerveDrive);
    }
}

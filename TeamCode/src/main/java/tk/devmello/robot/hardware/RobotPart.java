package tk.devmello.robot.hardware;

import tk.devmello.robot.bot.RobotFramework;
import tk.devmello.robot.stages.StageBuilder;
import tk.devmello.robot.stages.stage.Initial;
import tk.devmello.robot.stages.stage.Stop;
import tk.devmello.robot.util.User;
import static tk.devmello.robot.bot.General.mainUser;

/**
 * Represents a part of the robot like the drivetrain or the intake
 * When making a new part of the robot part make sure to extend this class
 * then override the init method
 */
public abstract class RobotPart extends StageBuilder {

    /**
     * The currentUser of this robot part
     */
    private User currentUser = User.NONE;

    /**
     * Method to "instantiate" the robot part
     * This automatically adds itself to the robotparts list
     */
    public void instantiate(){
        RobotFramework.allRobotParts.add(this);
    }

    /**
     * Init method (to be overriden)
     */
    public abstract void init();

    /**
     * Reset method (can be overriden)
     */
    public void reset(){}

    public abstract void halt();
    /**
     * Get the currentUser
     * @return currentUser
     */
    public User getUser(){
        return currentUser;
    }


    /**
     * Check the access of the user if they match the current user
     * This means that only the currentUser has access and all other users will be denied
     * In order to use a robotpart you must call switch user and then checkAccess
     * Returns true if the user access was granted
     * @param potentialUser
     */
    public synchronized boolean checkAccess(User potentialUser){
        if(potentialUser.equals(currentUser)) {

            return true;
        }else{

            return false;
        }
    }


    /**
     * Switch the user to the new user,
     * @param newUser the new user
     */
    public synchronized void switchUser(User newUser){
        currentUser = newUser;
    }


    /**
     * Use this robot part
     * NOTE: This must be called before the robot part can be used in a stage
     * @return initial
     */
    @Override
    public final Initial usePart(){return new Initial(() -> switchUser(User.ROFU));}

    /**
     * Return the robot part to the main user
     * NOTE: This must be called after the robot part is use in a stage
     * @return stop
     */
    @Override
    public final Stop returnPart(){return new Stop(() -> switchUser(mainUser));}


    /**
     * Maintain the state of this robot part
     */
    //TODO: Make this work
//    @Override
//    protected void maintain() { bot.addBackgroundTask(new BackgroundTask(() -> {checkAccess(User.AUTO); move(0);}));}

}

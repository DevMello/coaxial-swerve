package tk.devmello.robot.bot;

import tk.devmello.mellolib.math.geometry.framework.CoordinatePlane;
import tk.devmello.mellolib.threading.MelloThread;
import tk.devmello.mellolib.util.Iterator;
import tk.devmello.robot.hardware.RobotPart;
import tk.devmello.robot.util.BackgroundTask;
import tk.devmello.robot.util.Constants;
import tk.devmello.robot.util.User;

import java.util.ArrayList;

import static tk.devmello.robot.bot.General.gameTime;
import static tk.devmello.robot.bot.General.mainUser;

public class RobotFramework {

    public BackgroundFunctions backHandler;

    public RobotFunctions rfsHandler;

    /**
     * The allRobotParts arraylist contains all of the robotParts in the robot,
     * The RobotPart constructor automatically adds itself to this static arraylist
     */
    public static ArrayList<RobotPart> allRobotParts;

    public CoordinatePlane localPlane;
    public static MelloThread robotFunctionsThread;
    /**
     * The odometry thread is used to update odometry
     */

    /**
     * Background thread
     */
    public static MelloThread backgroundThread;
    /**
     * Independent thread
     */
    public final Configs configs = new Configs();

    public RobotFramework(){
        allRobotParts = new ArrayList<>();
        configs.setCurrentConfig();
        MelloThread.resetAllThreads();
        rfsHandler = new RobotFunctions();
        backHandler = new BackgroundFunctions();
        robotFunctionsThread = new MelloThread("RobotFunctionsThread", Constants.ROBOT_FUNCTIONS_REFRESH_RATE);
        backgroundThread = new MelloThread("BackgroundThread", Constants.BACKGROUND_THREAD_REFRESH_RATE);
        rfsHandler.init();
        backHandler.init();

    }

    public void init(){
        setUser(mainUser);
        //IEncoder.setEncoderReadingAuto();
        Iterator.forAll(allRobotParts, RobotPart::init);
        MelloThread.startAllThreads();
        gameTime.reset();
    }

    public void start() {
        rfsHandler.resume();
    }

    public void update(){
        checkAccess(mainUser);
        MelloThread.checkAllExceptions();

    }

    public void addBackgroundTask(BackgroundTask backgroundTask){
        backHandler.addBackgroundTask(backgroundTask);
    }

    /**
     * Sets the user to the new user specified
     * NOTE: This does not change the access of the user which must be updated explicity with checkAccess
     * @param newUser
     */
    public synchronized void setUser(User newUser){ Iterator.forAll(allRobotParts, part -> part.switchUser(newUser)); }

    /**
     * Checks the access of the potential user to all of the robot parts
     * This should be called every time a user wants to use the robot, to check if the current user privileges are updated
     * @param potentialUser
     */
    public synchronized void checkAccess(User potentialUser){ Iterator.forAll(allRobotParts, part -> part.checkAccess(potentialUser)); }

    public void cancelAll(){ cancelMovements(); cancelBackgroundTasks();  }

    public void cancelMovements(){ rfsHandler.emptyQueue();}

    public void cancelBackgroundTasks(){  backHandler.emptyTaskList();}
    /**
     * the stop method stops updating threads, and halts the robot
     * @link halt
     */
    public void stop(){
        cancelAll();
        MelloThread.stopUpdateThreads();
        halt();
    }

    public void halt(){ Iterator.forAll(allRobotParts, RobotPart::halt);}

}

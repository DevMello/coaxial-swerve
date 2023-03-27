package tk.devmello.mellolib.devlogger;

import tk.devmello.mellolib.devlogger.debugging.Log;
import tk.devmello.mellolib.devlogger.util.condition.Expectation;
import tk.devmello.devlogger.util.misc.*;
import tk.devmello.devlogger.util.storage.*;
import tk.devmello.mellolib.devlogger.util.misc.ConsoleColor;
import tk.devmello.mellolib.devlogger.util.misc.ExceptionCatcher;
import tk.devmello.mellolib.devlogger.util.misc.HWIDCheck;
import tk.devmello.mellolib.devlogger.util.storage.Storage;
import tk.devmello.mellolib.devlogger.util.time.TimeUtils;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Objects;

import static tk.devmello.mellolib.devlogger.util.storage.Storage.outDir;

/**
 * Logger class, used for logging information
 */

public class Logger {
    public String prefixName;
    public String author = "DevMello";
    public String version = "v0.0.1 Beta";

    protected String name;
    private final LinkedHashMap<String, Log> logs = new LinkedHashMap<>();
    private int logNum = 0;
    public Fault fault;
    public Storage storage;

    /*
    * This class constructor allows you to make a new Logger for whatever you need and set a custom prefix.
    * For example, you have a class named ExceptionCatcher which has too long of a name, and you don't want to log it everytime.
    * You can set the Prefix to EC and then everytime it logs it will print as EC - INFO,WARNING,ERROR ETC...
    * */
    public Logger(String name, String prefixName) {
        System.out.println(TimeUtils.getDate()+"[" + name + "] " + "Logger initialized.");
        this.name = name;
        this.prefixName = prefixName;
        this.fault = new Fault();
    }

    /*
     * This class constructor allows you to make a new Logger for whatever you need
     * It automatically sets the Prefix text to the same as the name of the Logger.
     */
    public Logger(String name) {
        System.out.println(TimeUtils.getDate()+"[" + name + "] " + "Logger initialized.");
        this.name = name;
        this.prefixName = name;
        this.fault = new Fault();
    }

    public void show(Object val){
//        General.telemetry.addData("Show", val);
//        updateOnShow();
        addData("Show", val);
    }
    public void show(String caption, Object val){
//        General.telemetry.addData(caption, val);
//        updateOnShow();
        addData(caption,val);
    }

    public void showAndRecord(String caption, Object val){
        show(caption, val);
        record(caption, val);
    }
    public void addData(String caption, Object value) {
        System.out.println(TimeUtils.getDate() + "[" + this.name + "] " + caption +": " + value);
    }

    public void record(String name, Object val){
        if(!logs.containsKey(name)){
            logs.put(name, new Log("Log #"+logNum+": "+name));
            logNum++;
        }
        Objects.requireNonNull(logs.get(name)).addNewOnly(val);
    }
    public void setupLog(String msg){
        System.out.println(TimeUtils.getDate()+ ConsoleColor.BLUE + "[" + name + "] " + ConsoleColor.RESET + msg);
    }

    public void setupLog(String module, String msg){
        System.out.println(TimeUtils.getDate()+ ConsoleColor.BLUE + "[" + module + "] " + ConsoleColor.RESET + msg);
    }
    public void consoleLogInfo(String msg) {
        String prefixText = prefixName + " - Info";
        System.out.println(TimeUtils.getDate() + ConsoleColor.BLUE + "[" + prefixText + "]" + ConsoleColor.RESET + " " + msg + ConsoleColor.RESET);

    }
    public void consoleLogWarn(String msg) {
        String prefixText = prefixName + " - Warn";
        System.out.println(TimeUtils.getDate() + ConsoleColor.YELLOW + "[" + prefixText + "]" + ConsoleColor.RESET + " " + msg + ConsoleColor.RESET);
    }
    public void consoleLogError(String msg) {
        String prefixText = prefixName + " - Error";
        System.out.println(TimeUtils.getDate() + ConsoleColor.RED + "[" + prefixText + "]" + ConsoleColor.RESET + " " + msg + ConsoleColor.RESET);
    }
    public void saveErrorOnCrash(Expectation e, String in) {
        String errorID = TimeUtils.getRandomId();
        consoleLogError("FATAL ERROR" + e.toString() + " ERROR ID: "+errorID);
        saveHWIDError(errorID, in, e);
        storage.saveItems();
    }
    public void saveErrorOnCrash(String in) {
        String errorID = TimeUtils.getRandomId();
        consoleLogError("FATAL ERROR\n ERROR ID: "+errorID);
        saveHWIDError(errorID, in);
        storage.saveItems();
    }
    private void saveHWIDError(String errorID,String in,Expectation e)  {
        ExceptionCatcher.catchIO(() -> {
            PrintWriter out = new PrintWriter(outDir.getAbsolutePath()+"/" + errorID + ".txt");
            out.println(HWIDCheck.getHwid());
            out.println("ERROR ID: " + errorID);
            out.println("DevLoggger by " + author +" version "+version);
            out.println("Exception: " + e.toString());
            out.println("Final Words: "+ in);
            out.flush();
            out.close();
        });
    }
    private void saveHWIDError(String errorID,String in)  {
        ExceptionCatcher.catchIO(() -> {
            PrintWriter out = new PrintWriter(outDir.getAbsolutePath()+"/" + errorID + ".txt");
            out.println(HWIDCheck.getHwid());
            out.println("ERROR ID: " + errorID);
            out.println("DevLoggger by " + author +" version "+version);
            out.println("Final Words: "+ in);
            out.flush();
            out.close();
        });
    }
}

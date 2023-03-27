package tk.devmello.mellolib.devlogger.util.misc;

public class HWIDCheck {

    public static String sys = System.getProperty("user.name") + " " + System.getenv("COMPUTERNAME") + " " + System.getenv("os") + " " + System.getProperty("os.name") + " " + System.getenv("PROCESSOR_IDENTIFIER") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version") + " " + System.getProperty("user.language") + " " + System.getenv("SystemRoot") + " " + System.getenv("HOMEDRIVE") + " " + System.getenv("PROCESSOR_LEVEL") + " " + System.getenv("PROCESSOR_REVISION") + " " + System.getenv("PROCESSOR_IDENTIFIER") + " " + System.getenv("PROCESSOR_ARCHITECTURE") + " " + System.getenv("PROCESSOR_ARCHITEW6432") + " " + System.getenv("NUMBER_OF_PROCESSORS") + " " + System.getProperty("java.version");
    static String pHwid = "DevLoader-" + sys;
    public static String getHwid(){
        return pHwid;
    }
    public static void causeStackOverflow() {
        causeStackOverflow();
    }

}

package tk.devmello.robot.util.mode;

public interface Modes {

    enum GamepadMode implements Mode.ModeType { NORMAL, AUTOMATED }
    enum Drive implements Mode.ModeType {FAST, MEDIUM, SLOW}
    Mode driveMode = new Mode(Drive.class);
}

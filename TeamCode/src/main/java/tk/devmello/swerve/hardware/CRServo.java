package tk.devmello.swerve.hardware;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;


public class CRServo extends Motor implements PwmControl {


    protected com.qualcomm.robotcore.hardware.CRServo crServo;


    public CRServo(HardwareMap hMap, String id) {
        crServo = hMap.get(com.qualcomm.robotcore.hardware.CRServo.class, id);

    }

    @Override
    public void set(double output) {
        crServo.setPower(output);
    }

    @Override
    public double get() {
        return crServo.getPower();
    }

    @Override
    public void setInverted(boolean isInverted) {
        crServo.setDirection(isInverted ? com.qualcomm.robotcore.hardware.CRServo.Direction.REVERSE
                : com.qualcomm.robotcore.hardware.CRServo.Direction.FORWARD);
    }

    @Override
    public boolean getInverted() {
        return crServo.getDirection() == com.qualcomm.robotcore.hardware.CRServo.Direction.REVERSE;
    }

    @Override
    public void disable() {
        crServo.close();
    }

    public void stop() {
        set(0);
    }

    @Override
    public void stopMotor() {
        stop();
    }

    @Override
    public void setPwmRange(PwmRange range) {

    }

    @Override
    public PwmRange getPwmRange() {
        return null;
    }

    @Override
    public void setPwmEnable() {

    }

    @Override
    public void setPwmDisable() {

    }

    @Override
    public boolean isPwmEnabled() {
        return false;
    }
}
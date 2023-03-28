package tk.devmello.robot.util.mode;

import tk.devmello.mellolib.codeseg.CodeSeg;
import tk.devmello.mellolib.codeseg.ReturnCodeSeg;
import tk.devmello.mellolib.devlogger.util.condition.Expectation;
import tk.devmello.mellolib.devlogger.util.condition.Magnitude;
import tk.devmello.robot.bot.General;
import tk.devmello.robot.stages.stage.Main;
import tk.devmello.robot.stages.stage.Stage;

import java.util.HashMap;

import static tk.devmello.robot.stages.StageBuilder.exitAlways;

public class Mode {
    /**
     * Class for creating enums that have a value
     */

    /**
     * ModeType enum to represent the different values of the mode
     */


    private final HashMap<ModeType, Double> valueMap = new HashMap<>();

    private ModeType currentMode;
    private final ModeType[] types;

    public Mode(Class<? extends Enum<? extends ModeType>> types){
        this.types = (ModeType[]) types.getEnumConstants();
        currentMode = this.types[0];
    }

    public Mode set(ModeType type, double value){
        valueMap.put(type, value);
        return this;
    }


    public void cycleUp() { for (int i = 0; i < types.length-1; i++) { if(modeIs(types[i])){ set(types[i+1]); return; }} if(modeIs(types[types.length-1])){set(types[0]);} }
    public void cycleDown() { for (int i = 1; i < types.length; i++) { if(modeIs(types[i])){ set(types[i-1]); return; }} if(modeIs(types[0])){set(types[types.length-1]);} }
    public boolean modeIs(ModeType other){ return currentMode.equals(other); }
    public ReturnCodeSeg<Boolean> isMode(ModeType other){return () -> currentMode.equals(other); }
    public double getValue(ModeType modeType){
        if(General.logger.fault != null){ General.logger.fault.check("Value for mode" + modeType.toString() + "not defined", Expectation.EXPECTED, Magnitude.CRITICAL, valueMap.containsKey(modeType), true); }
        return valueMap.get(modeType);
    }

    public double getValue(){ return getValue(currentMode); }



    public ModeType get(){ return currentMode; }
    public Stage ChangeMode(ModeType newMode){return new Stage(new Main(() -> currentMode = newMode), exitAlways()); }
    public Stage ChangeMode(ReturnCodeSeg<ModeType> newMode){return new Stage(new Main(() -> currentMode = newMode.run()), exitAlways()); }
    public void set(ModeType mode){ this.currentMode = mode; }
    public CodeSeg setTo(ModeType mode){return () -> {this.currentMode = mode;};}

    public void toggle(ModeType one, ModeType two){ if(modeIs(one)){set(two);}else{set(one);} }

    public interface ModeType extends Decision {};

}

package tk.devmello.mellolib.util;

import android.annotation.SuppressLint;
import tk.devmello.mellolib.codeseg.CodeSeg;
import tk.devmello.mellolib.codeseg.ParameterCodeSeg;
import tk.devmello.mellolib.codeseg.ReturnCodeSeg;
import tk.devmello.mellolib.codeseg.ReturnParameterCodeSeg;

import java.util.*;
import java.util.function.ToDoubleFunction;

import static tk.devmello.robot.General.log;


public interface Iterator {
    /**
     * Iterator interface, used for making loops with conditions
     */


    /**
     * Private timer for internal methods
     */
    Timer time = new Timer();

    /**
     * Method to define the condition to check (make this return true for default behavior)
     * @return true
     */
    boolean condition();

    /**
     * Do something while the condition is true (default behavior)
     * @param code
     */
    default void whileActive(CodeSeg code){
        while (condition()){
            code.run();
        }
    }

    /**
     * Do something while the condition is true and the active condition is true
     * @param code
     */
    default void whileActive(ReturnCodeSeg<Boolean> active, CodeSeg code){
        while (condition() && active.run()){
            code.run();

        }

    }

    default void whileNotExit(ReturnCodeSeg<Boolean> exit, CodeSeg code){

        while (condition()){
            code.run();

            if(exit.run()){ break; }
        }

    }

    /**
     * Do something for some amount of time
     * @param code
     * @param secs
     */
    default void whileTime(CodeSeg code, double secs){

        time.reset();
        while (condition() && time.seconds() < secs){
            code.run();

        }

    }

    /**
     * Pause for some amount of seconds
     * @param secs
     */
    default void pause(double secs){ whileTime(() -> {}, secs); }


    /**
     * Static helper methods for looping through lists
     * @param list
     * @param code
     * @param <T>
     */
    static<T> void forAll(ArrayList<T> list, ParameterCodeSeg<T> code){
        for(T obj: list){
            code.run(obj);
        }
    }

    static<T> void forAll(Collection<T> list, ParameterCodeSeg<T> code){
        for(T obj: list){
            code.run(obj);
        }
    }

    static<T> void forAll(TreeMap<?, T> list, ParameterCodeSeg<T> code){
        for(T obj: list.values()){
            code.run(obj);
        }
    }

    static<K, V> void forAll(HashMap<K, V> list, DoubleParameterCodeSegDifferent<K, V> code){
        for(Map.Entry<K, V> entry : list.entrySet()){
            code.run(entry.getKey(), entry.getValue());
        }
    }

    static<T> void forAll(T[] list, ParameterCodeSeg<T> code){
        for(T obj: list){
            code.run(obj);
        }
    }

    static <T> int forAllCount(ArrayList<T> list, ReturnParameterCodeSeg<T, Boolean> code){
        int count = 0;
        for(T obj: list){
            count += code.run(obj) ? 1:0;
        }
        return count;
    }

    static <T> boolean forAllConditionOR(ArrayList<T> list, ReturnParameterCodeSeg<T, Boolean> code){
        for(T obj: list){
            if(code.run(obj)){
                return true;
            }
        }
        return false;
    }

    static <T> boolean forAllConditionAND(ArrayList<T> list, ReturnParameterCodeSeg<T, Boolean> code){
        for(T obj: list){
            if(!code.run(obj)){
                return false;
            }
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    static <T> ArrayList<T> forAllOfType(ArrayList<? super T> list, Class<T> type){
        ArrayList<T> out = new ArrayList<>();
        for(Object obj: list){
            if(obj.getClass().equals(type)){
                out.add((T) obj);
            }
        }
        return out;
    }

    @SuppressWarnings("unchecked")
    static <T> ArrayList<T> forAllOfExtendedType(ArrayList<? super T> list, Class<T> type){
        ArrayList<T> out = new ArrayList<>();
        for(Object obj: list){
            if(type.isInstance(obj)){
                out.add((T) obj);
            }
        }
        return out;
    }

    static void forAllRun(ArrayList<CodeSeg> codeSegments){ forAll(codeSegments, CodeSeg::run); }

    static double forAllAverage(ReturnCodeSeg<Double> code, int number){
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            values.add(code.run());
        }
        return forAllAverage(values);
    }

    static <T> double forAllAverage(ArrayList<T> list, ReturnParameterCodeSeg<T, Double> code){
        final double[] sum = {0};
        Iterator.forAll(list, l-> sum[0]+=code.run(l) );
        return (double)sum[0]/list.size();
    }

    static double forAllAverage(ArrayList<Double> list){
        final double[] sum = {0};
        Iterator.forAll(list, l->sum[0]+=l);
        return sum[0] / list.size();
    }

    static <T> T forAllCompareMax(List<T> list, ReturnParameterCodeSeg<T, Double> code){
        ArrayList<Double> values = new ArrayList<>();
        Iterator.forAll(list, o -> values.add(code.run(o)));
        return list.get(maxIndex(values));
    }

    static <T> T forAllCompareMin(List<T> list, ReturnParameterCodeSeg<T, Double> code){
        List<Double> values = new ArrayList<>();
        Iterator.forAll(list, o -> values.add(code.run(o)));
        return list.get(minIndex(values));
    }

    static int minIndex(List<Double> arr){ int ind = 0; double min = arr.get(0); for (int i = 0; i < arr.size() ; i++) { if(arr.get(i) < min){min = arr.get(i); ind = i;} } return ind; }

    static int maxIndex(List<Double> arr){ int ind = 0; double max = arr.get(0); for (int i = 0; i < arr.size() ; i++) { if(arr.get(i) > max){max = arr.get(i); ind = i;} } return ind; }

    static int minIndex(double... arr){ int ind = 0; double min = arr[0]; for (int i = 0; i < arr.length ; i++) { if(arr[i] < min){min = arr[i]; ind = i;} } return ind; }

    static <T> void removeCondition(ArrayList<T> list, ReturnParameterCodeSeg<T, Boolean> condition){ for (int i = list.size() - 1; i >= 0; --i) { if (condition.run(list.get(i))) { list.remove(i); } } }

    @SuppressLint("NewApi")
    static <T> void sort(ArrayList<T> list, ToDoubleFunction<? super T> key){ Collections.sort(list, Comparator.comparingDouble(key)); }

    @FunctionalInterface
    interface DoubleParameterCodeSeg<P> { void run(P input, P input2); }

    @FunctionalInterface
    interface DoubleParameterCodeSegDifferent<A, B> { void run(A input, B input2); }

    static <T> void forAllPairs(ArrayList<T> list, DoubleParameterCodeSeg<T> code){
        if(list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) { code.run(list.get(i), list.get(i+1)); }
            code.run(list.get(list.size()-1), list.get(0));
        }
    }
}

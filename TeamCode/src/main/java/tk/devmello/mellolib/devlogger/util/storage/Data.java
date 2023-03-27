package tk.devmello.mellolib.devlogger.util.storage;

import java.util.ArrayList;


public class Data<X, Y>{
    /**
     * Used to define data that changes over time
     * X is the type of input (x values)
     * Y is the type of output (y values)
     */


    /**
     * The name of the data (title)
     */
    private final String name;
    /**
     * Input item to store inputs
     */
    private final Item<ArrayList<X>> input;
    /**
     * Output item to store outputs
     */
    private final Item<ArrayList<Y>> output;

    /**
     * Create the data using the parameters
     * @param name
     * @param input
     * @param output
     */
    public Data(String name, ArrayList<X> input, ArrayList<Y> output){
        this.name = name;
        this.input = new Item<>(getInputName(name), input);
        this.output = new Item<>(getOutputName(name), output);
    }

    /**
     * Get the name, input, output, input name, or output name
     * @return property
     */
    public String getName(){
        return name;
    }
    public Item<ArrayList<X>> getInput(){
        return input;
    }
    public Item<ArrayList<Y>> getOutput(){
        return output;
    }
    public static String getInputName(String name){ return "Input " + name; }
    public static String getOutputName(String name){ return "Output " + name; }

    /**
     * Convert the string representation of the data into a data object
     * @param name
     * @param input
     * @param output
     * @return data
     */
    public static Data<?,?> fromString(String name, String input, String output){
        return new Data<>(name, (ArrayList<?>) Item.fromString(getInputName(name), input).getValue(), (ArrayList<?>) Item.fromString(getOutputName(name), output).getValue());
    }

}
package tk.devmello.mellolib.devlogger.debugging;

import tk.devmello.mellolib.devlogger.util.storage.Item;

import java.util.ArrayList;

public class Log {
    /**
     * Item containing name and values
     */
    private final Item<ArrayList<Object>> item;

    /**
     * Current object (last one in the list)
     */
    private Object currentObject = null;

    /**
     * Constructor for log
     * @param name
     */
    public Log(String name){
        item = new Item<>(name, new ArrayList<>());
    }

    /**
     * Add a value to the log
     * @param o
     */
    public void add(Object o){
        currentObject = o;
        item.getValue().add(o);
    }

    /**
     * Add a value only if its different then the previous one
     * @param
     */
    public void addNewOnly(Object o){
        if(!o.equals(getCurrentObject())){ add(o); }
    }

    /**
     * Get the name of the log
     * @return name
     */
    public String getName(){
        return item.getName();
    }

    /**
     * Get the current object, if values has nothing return null
     * @return current object
     */
    public Object getCurrentObject(){
        return currentObject;
    }

    /**
     * Get the list of values
     * @return values
     */
    public ArrayList<Object> getValues(){
        return item.getValue();
    }

}

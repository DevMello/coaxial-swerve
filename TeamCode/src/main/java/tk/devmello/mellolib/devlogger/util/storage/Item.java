package tk.devmello.mellolib.devlogger.util.storage;


import tk.devmello.mellolib.devlogger.Logger;
import tk.devmello.mellolib.devlogger.util.condition.Expectation;
import tk.devmello.mellolib.devlogger.util.condition.Magnitude;

import java.util.ArrayList;
import java.util.Objects;

public class Item<T> {
    /**
     * Item represents a item, should be created as follows
     * For example if creating an item with a int value then
     * Item item = new Item<>("yes", 1)
     */

    protected static Logger logger;
    /**
     * Name of the item
     */
    private final String name;
    /**
     * Value of the item as a type T
     */
    private final T value;
    /**
     * The type of item (string, double, etc..)
     */
    private final ItemType type;

    /**
     * Constructor, pass in the name and the value
     * NOTE: This will automatically generate the type of the item from the value
     * @param name
     * @param value
     */
    public Item(String name, T value){
        this.name = name;
        this.value = value;
        this.type = getTypeFromObject();
        this.logger = new Logger("Item", "Item");
    }
    /**
     * Static method to create a new item from a raw string
     * @param name
     * @param rawString
     * @return item
     */
    public static Item<?> fromString(String name, String rawString){
        return new Item<>(name, getObjectFromType(rawString));
    }

    /**
     * Get the name
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Get the value
     * @return value
     */
    public T getValue(){
        return value;
    }

    /**
     * Get the itemtype from the object given
     * NOTE: If the type does not match any known types other will be returned
     * @return itemtype
     */
    private ItemType getTypeFromObject(){
        if(value instanceof String){
            return ItemType.STRING;
        }else if(value instanceof Integer){
            return ItemType.INT;
        }else if(value instanceof Float){
            return ItemType.FLOAT;
        }else if(value instanceof Double){
            return ItemType.DOUBLE;
        }else if(value instanceof Boolean){
            return ItemType.BOOLEAN;
        }else if(value instanceof ArrayList){
            return ItemType.ARRAYLIST;
        }else{
            return ItemType.OTHER;
        }
    }

    /**
     * Get the object from the rawString
     * @param rawString
     * @return object
     */
    private static Object getObjectFromType(String rawString){
        /**
         * Split the string at the :
         */
        String rawType = rawString.split(":")[0];
        String rawValue = rawString.split(":")[1];
        /**
         * Using the type get the value in the appropriate format
         */
        switch (Objects.requireNonNull(ItemType.fromString(rawType))){
            case STRING:
                return rawValue;
            case INT:
                return Integer.valueOf(rawValue);
            case FLOAT:
                return Float.valueOf(rawValue);
            case DOUBLE:
                return Double.valueOf(rawValue);
            case BOOLEAN:
                return Boolean.valueOf(rawValue);
            case ARRAYLIST:
                return Storage.gson.fromJson(rawValue, ArrayList.class);
            case OTHER:
                logger.fault.check("Are you sure you want the raw value of an object of type other?");
                return rawValue;
        }
        logger.fault.warn("Object does not match any known type", Expectation.EXPECTED, Magnitude.MINOR);
        return null;
    }

    /**
     * String representation of the object, has both the type and the value
     * This is used to get the object back from a raw string
     * @return string of type and value
     */
    @Override
    public String toString(){
        if(!type.equals(ItemType.ARRAYLIST)) {
            return type + ":" + value.toString();
        }else{
            return type + ":" + Storage.gson.toJson(value);
        }
    }

    /**
     * The type of item
     */
    public enum ItemType {
        STRING,
        INT,
        FLOAT,
        DOUBLE,
        BOOLEAN,
        ARRAYLIST,
        OTHER;

        /**
         * Gets the item type from a string by seeing if the toString of the itemtype equals
         * the toString of the desired string
         * @param s
         * @return item type
         */
        public static ItemType fromString(String s){
            for (ItemType t: ItemType.values()){
                if(s.equals(t.toString())){ return t; }
            }
            logger.fault.warn("ItemType does not match any known type", Expectation.SURPRISING, Magnitude.MINOR);
            return null;
        }
    }
}

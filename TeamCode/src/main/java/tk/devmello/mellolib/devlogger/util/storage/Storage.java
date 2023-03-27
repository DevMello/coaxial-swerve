package tk.devmello.mellolib.devlogger.util.storage;

import com.google.gson.Gson;
import tk.devmello.mellolib.devlogger.Logger;
import tk.devmello.mellolib.devlogger.util.condition.Expectation;
import tk.devmello.mellolib.devlogger.util.condition.Magnitude;
import tk.devmello.mellolib.devlogger.util.misc.ExceptionCatcher;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    protected Logger logger;
    /**
     * List of items to store
     */
    private ArrayList<Item<?>> items = new ArrayList<>();

    /**
     * Gson object used for parsing json
     */
    public static Gson gson = new Gson();

    /**
     * Out directory file
     */
    public static File outDir;


    /**
     * Storage constructor, created directories FTC_Files, and current
     */
    public Storage(){
        //File filepath = Environment.getExternalStorageDirectory();
        File filepath = new File(System.getenv("APPDATA"));
        File ftcDir = setDir(filepath.getAbsolutePath()+"/FTC_Files/", "FTC Dir");
        outDir = setDir(ftcDir.getAbsolutePath() + "/current/", "Out Dir");
        logger = new Logger("Storage Logger", "Storage" );
    }

    /**
     * Add an item given a name and a value
     * @param name
     * @param value
     */
    public <T> void addItem(String name, T value) { items.add(new Item<>(name, value)); }

    /**
     * Add item given an item object
     * @param item
     * @param <T>
     */
    public <T> void addItem(Item<T> item) { items.add(item); }

    /**
     * Add data by giving an array of inputs (x values) and outputs (y values)
     * @param name
     * @param input
     * @param output
     * @param <X>
     * @param <Y>
     */
    public <X, Y> void addData(String name, ArrayList<X> input, ArrayList<Y> output) {
        Data<X, Y> data = new Data<>(name, input, output);
        addItem(data.getInput());
        addItem(data.getOutput());
    }

    /**
     * Save the items currently in the arraylist of items
     * NOTE: This generates a bunch of text files in the current directory with the name of the item
     * as the name of the text file and the value in the file
     */
    public void saveItems(){
        logger.fault.log("No items to save", Expectation.SURPRISING, Magnitude.MINOR, items.isEmpty(), false);
        for(Item<?> i: items) {
            saveText(i.getName(), i.toString());
        }
    }

    /**
     * Removes all of the items in the arraylist of items
     */
    public void emptyItems(){
        items = new ArrayList<>();
    }


    public void saveAndClear(){ saveItems(); emptyItems(); }


    /**
     * Get the number of items that are currently in storage (for this session)
     * @return numItems
     */
    public int numItems(){
        return items.size();
    }

    /**
     * Get the item using the name
     * NOTE: This gets it from the storage not the arraylist so the arraylist could be empty when this is called
     * @param name
     * @return itemValue
     */
    public Item<?> getItem(String name){
        return Item.fromString(name, readText(name));
    }

    /**
     * Get the data using the name
     * @param name
     * @return data
     */
    public Data<?, ?> getData(String name){
        return Data.fromString(name, readText( Data.getInputName(name)), readText(Data.getOutputName(name)));
    }

    /**
     * Save the text using the directory name, the filename, and the string to save
     * @param filename
     * @param in
     */
    private void saveText(String filename, String in)  {
        ExceptionCatcher.catchIO(() -> {
            PrintWriter out = new PrintWriter(outDir.getAbsolutePath()+"/" + filename + ".txt");
            out.println(in);
            out.flush();
            out.close();
        });
    }

    private void saveText(String filename)  {
        ExceptionCatcher.catchIO(() -> {
            PrintWriter out = new PrintWriter(outDir.getAbsolutePath()+"/" + filename + ".txt");
            out.flush();
            out.close();
        });
    }

    /**
     * Read the text from the directory name, and the filename
     * @param filename
     * @return text
     */
    private String readText(String filename) {
        final String[] out = {""};
        saveText(filename);
        ExceptionCatcher.catchIO(() -> {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(outDir.getAbsolutePath()+"/" + filename + ".txt")));
            out[0] = scan.nextLine();
        });
        return out[0];
    }

    /**
     * Create the desired directory if it doesn't exist, or return the file if it does
     * @return directory
     */
    private File setDir(String dirpath, String name){
        File dir = new File(dirpath);
        if(!dir.exists()) {
            logger.record("Creating " + name+ ", Succeeded:", dir.mkdir());
        }
        return dir;
    }
}

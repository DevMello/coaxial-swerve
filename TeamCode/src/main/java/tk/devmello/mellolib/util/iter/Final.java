package tk.devmello.mellolib.util.iter;

public class Final<T> {
    private volatile T object;
    public Final(T obj){ set(obj); }
    public void set(T obj){ object = obj; }
    public T get(){ return object; }
}

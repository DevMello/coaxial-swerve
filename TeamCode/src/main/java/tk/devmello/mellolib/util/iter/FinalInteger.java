package tk.devmello.mellolib.util.iter;

public class FinalInteger extends Final<Integer>{
    public FinalInteger() { super(0); }
    public FinalInteger(int i){ super(i);}
    public void increment(int inc){ set(get()+inc);}
    public boolean equals(int i) { return get() == i; }
}

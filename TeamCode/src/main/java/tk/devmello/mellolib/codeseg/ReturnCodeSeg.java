package tk.devmello.mellolib.codeseg;

/**
 * Used to return a certain type of object
 * Created as follows: () -> {return <something>}
 * @param <R>
 */
@FunctionalInterface
public interface ReturnCodeSeg<R> {
    R run();
}

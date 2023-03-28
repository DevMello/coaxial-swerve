package tk.devmello.mellolib.codeseg;

/**
 * Used to pass in code (Code Segment)
 * Created as follows: () -> <code to run> or
 * () -> {<multiline code to run>}
 */
@FunctionalInterface
public interface CodeSeg {
    void run();
}
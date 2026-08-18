import java.util.concurrent.*;
import java.util.function.Function;

/**
 * Memoizer: a cache that computes the value only once per key, even under load.
 * The trick is to store a Future instead of the value itself. A thread that comes second
 * finds the Future already in the map and just waits on get() instead of starting
 * a second computation.
 * The loop is here to retry when the cached computation was cancelled.
 */
@SuppressWarnings("unused")
public class FutureCache<T, R> implements Function<T, R> {

    private final ConcurrentMap<T, Future<R>> map = new ConcurrentHashMap<>();
    private final Function<T, R> function;

    public FutureCache(Function<T, R> function) {
        this.function = function;
    }

    @Override
    public R apply(T t) {
        while (true) {
            // TODO broken as written: the FutureTask is created but nobody runs it, so get() blocks forever.
            //  The book version calls run() on the task when we are the thread that put it in the map.
            Future<R> future = map.computeIfAbsent(t, x -> new FutureTask<>(() -> function.apply(x)));
            try {
                return future.get();
            } catch (CancellationException e) {
                map.remove(t);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

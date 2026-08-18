import java.util.HashSet;
import java.util.Set;

/**
 * Immutable objects are always thread-safe, so they are the cheapest way to share state.
 * Here the set is filled in the constructor and never handed out, so nobody can change it later.
 * <p>
 * Immutable if:
 * 1. State can't be modified after construction
 * 2. All its fields are 'final'
 * 3. Properly constructed: 'this' doesn't escape during construction
 */
@SuppressWarnings("unused")
public class ImmutableOverMutable {

    private final Set<String> stooges = new HashSet<>();

    public ImmutableOverMutable() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }
}

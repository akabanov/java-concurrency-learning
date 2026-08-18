/**
 * Shows that 'n != n' can actually be true. If the object is published unsafely,
 * one thread can see a stale value (0) on the first read and the real value on the second.
 * The fix is to make n final or to publish the object safely.
 */
@SuppressWarnings("unused")
public class SanityCheck {

    private int n;

    public SanityCheck(int n) {
        this.n = n;
    }

    // may fail if the object isn't properly published
    public void assertSanity() {
        if (n != n) throw new AssertionError();
    }
}

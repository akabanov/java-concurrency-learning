import javax.swing.*;

/**
 * The fix for ImplicitEscape: private constructor plus a static factory.
 * The object is fully constructed first, and only then the listener is registered,
 * so 'this' never escapes early.
 */
@SuppressWarnings("unused")
public class PatternAntiEscape {

    private PatternAntiEscape() {
    }

    private void doStuff() {
    }

    public static PatternAntiEscape newActionFor(JButton button) {
        PatternAntiEscape instance = new PatternAntiEscape();
        button.addActionListener(e -> instance.doStuff());
        return instance;
    }
}

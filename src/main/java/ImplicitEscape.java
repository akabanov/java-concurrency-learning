import javax.swing.*;

/**
 * How NOT to do it. Registering a listener from the constructor publishes 'this'
 * before the object is fully built, so another thread can see a half constructed object.
 * See PatternAntiEscape for the fix.
 */
@SuppressWarnings("unused")
public class ImplicitEscape {

    public ImplicitEscape(JButton button) {
        // implicit 'this' escape
        button.addActionListener(e -> doStuff());
    }

    private void doStuff() {
    }
}

package esp.ui.views;

import esp.events.EventSystem;

/**
 * View
 *
 * @author Santiago Barreiro
 */
public abstract class View {

    // ATTRIBUTES
    private final EventSystem es;

    // CONSTRUCTORS
    public View (EventSystem es) {
        this.es = es;
    }

    // GETTERS & SETTERS
    public EventSystem getEventSystem() {
        return es;
    }

    // METHODS
    abstract void render();
}

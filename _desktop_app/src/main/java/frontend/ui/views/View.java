package frontend.ui.views;

import backend.events.EventSystem;
import frontend.ui.ImGuiLayer;

/**
 * View
 *
 * @author Santiago Barreiro
 */
public abstract class View {

    // ATTRIBUTES
    private final ImGuiLayer layer;
    private final EventSystem es;

    // CONSTRUCTORS
    public View (ImGuiLayer layer, EventSystem es) {
        this.layer = layer;
        this.es = es;
    }

    // GETTERS & SETTERS
    public EventSystem getEventSystem() {
        return es;
    }

    public ImGuiLayer getLayer() {
        return layer;
    }

    // METHODS
    abstract void render();
}

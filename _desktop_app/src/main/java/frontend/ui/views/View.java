package frontend.ui.views;

import backend.api.IEventSystem;
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
    private final IEventSystem es;

    // CONSTRUCTORS
    public View (ImGuiLayer layer, IEventSystem es) {
        this.layer = layer;
        this.es = es;
    }

    // GETTERS & SETTERS
    public IEventSystem getEventSystem() {
        return es;
    }

    public ImGuiLayer getLayer() {
        return layer;
    }

    // METHODS
    abstract void render();
}

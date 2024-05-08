package esp.events;

import esp.api.IEvent;

/**
 * Event
 *
 * @author Santiago Barreiro
 */
public class UIEvent implements IEvent {

    public enum Type {
        CREATE_TASK,
        MODIFIED_TASK,
        REMOVED_TASK,
        USER_EVENT
    }

    // ATTRIBUTES
    private final Type type;
    private final Object payload;
    private final int handleIterations;
    private boolean isHandled;

    // CONSTRUCTORS
    public UIEvent(Type type) {
        this.type = type;
        this.payload = null;
        this.handleIterations = 1;
        this.isHandled = false;
    }

    public UIEvent(Type type, int handleIterations) {
        this.type = type;
        this.payload = null;
        this.handleIterations = handleIterations;
        this.isHandled = false;
    }

    public UIEvent(Type type, int handleIterations, Object payload) {
        this.type = type;
        this.payload = payload;
        this.handleIterations = handleIterations;
        this.isHandled = false;
    }

    // GETTERS & SETTERS
    @Override
    public Type getEventType() {
        return type;
    }

    @Override
    public int getHandleIterations() {
        return handleIterations;
    }

    @Override
    public Object getPayload() {
        return payload;
    }

    @Override
    public boolean isHandled() {
        return isHandled;
    }

    @Override
    public void setHandled() {
        this.isHandled = true;
    }
}

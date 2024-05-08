package esp.events;

import esp.api.IEvent;

/**
 * Event
 *
 * @author Santiago Barreiro
 */
public class Event implements IEvent {

    public enum Type {
        CREATE_TASK,
        USER_EVENT
    }

    // ATTRIBUTES
    private final Type type;
    private final Object payload;
    private final int handleIterations;
    private boolean isHandled;

    // CONSTRUCTORS
    public Event(Type type, int handleIterations, Object payload) {
        this.type = type;
        this.payload = payload;
        this.handleIterations = handleIterations;
        this.isHandled = false;
    }

    // GETTERS & SETTERS
    @Override
    public Enum<?> getEventType() {
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

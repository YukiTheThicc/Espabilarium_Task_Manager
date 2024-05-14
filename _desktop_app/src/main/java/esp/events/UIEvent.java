package esp.events;

import esp.api.IEvent;

/**
 * Event
 *
 * @author Santiago Barreiro
 */
public record UIEvent(Type type, Object payload, int handleIterations) implements IEvent {

    public enum Type {
        CREATE_TASK,
        SAVE_TASK,
        USER_EVENT
    }

    // CONSTRUCTORS
    public UIEvent(Type type) {
        this(type, null, -1);
    }

    public UIEvent(Type type, int handleIterations) {
        this(type, null, handleIterations);
    }

    public UIEvent(Type type, Object payload) {
        this(type, payload, -1);
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
}

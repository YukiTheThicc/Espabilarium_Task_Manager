package frontend.events;

import backend.api.IEvent;

/**
 * Event
 *
 * @author Santiago Barreiro
 */
public record Event(Type type, Object payload, int handleIterations) implements IEvent {

    public enum Type {
        STOW_TASK,
        UPDATE_TASK,
        USER_EVENT
    }

    // CONSTRUCTORS
    public Event(Type type) {
        this(type, null, -1);
    }

    public Event(Type type, int handleIterations) {
        this(type, null, handleIterations);
    }

    public Event(Type type, Object payload) {
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

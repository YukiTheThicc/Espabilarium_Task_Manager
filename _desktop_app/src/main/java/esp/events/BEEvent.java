package esp.events;

import esp.api.IEvent;

/**
 * BEEvent
 *
 * @author Santiago Barreiro
 */
public record BEEvent(Type type, Object payload, int handleIterations) implements IEvent {

    public enum Type {
        CREATED_TASK,
        SAVED_TASK,
        REMOVED_TASK
    }

    // CONSTRUCTORS
    public BEEvent(Type type) {
        this(type, null, -1);
    }

    public BEEvent(Type type, int handleIterations) {
        this(type, null, handleIterations);
    }

    public BEEvent(Type type, Object payload) {
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

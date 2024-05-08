package esp.api;

/**
 * IEvent. Interface for the events handled by the event system of Espabilarium
 *
 * @author Santiago Barreiro
 */
public interface IEvent {

    /**
     * Returns how many times the event can be handled.
     * @return Enum value fot the tpe
     */
    int getHandleIterations();

    /**
     * Returns an Enum value defining the type of one event
     * @return Enum value fot the tpe
     */
    Enum<?> getEventType();

    /**
     * Returns the payload of the event
     * @return Object payload of the event. Can be null if the event has no payload
     */
    Object getPayload();

    /**
     * Returns if the event has been handled
     * @return True if the event has been already handled, false otherwise
     */
    boolean isHandled();

    /**
     * Sets the event as handled
     */
    void setHandled();
}

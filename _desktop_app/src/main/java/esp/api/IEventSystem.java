package esp.api;

/**
 * IEventSystem
 *
 * @author Santiago Barreiro
 */
public interface IEventSystem {

    /**
     * Registers an observer in this system
     * @param newObserver New observer to be registered
     * @param subscriptions List of enum values which the observer will be notified of
     */
     void addObserver(IEvent.Observer newObserver, Enum<?>[] subscriptions);

    /**
     * Throws a new event into the event stack of this system
     */
    void throwEvent(IEvent event);

    /**
     * Handles the queued events
     */
    void handleEvents();
}

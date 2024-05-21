package backend.api;

/**
 * IEventSystem
 *
 * @author Santiago Barreiro
 */
public interface IEventSystem {

    /**
     * Registers an observer in this system.
     * @param newObserver New observer to be registered
     * @param subscriptions List of enum values which the observer will be notified of
     */
     void attachObserver(Enum<?>[] subscriptions, IEvent.Observer newObserver);

    /**
     * Registers an observer in this system. Used to observe only specific objects by their id
     * @param newObserver New observer to be registered
     * @param subscription String value which the observer will be notified of
     */
    void attachObserver(String subscription, IEvent.Observer newObserver);

    /**
     * Removes the observer from the specified subscriptions.
     * all subscriptions
     * @param toDetach The observer to detach
     * @param subscriptions List of subscriptions to be detached from.
     */
    void detachObserver(Enum<?>[] subscriptions, IEvent.Observer toDetach);

    /**
     * Removes the observer from the specified subscriptions.
     * all subscriptions
     * @param toDetach The observer to detach
     * @param subscription String value which the observer will be detached from
     */
    void detachObserver(String subscription, IEvent.Observer toDetach);

    /**
     * Throws a new event into the event stack of this system
     */
    void throwEvent(IEvent event);

    /**
     * Handles the queued events
     */
    void dispatchEvents();
}

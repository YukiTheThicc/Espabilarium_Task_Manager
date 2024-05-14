package esp.api;

/**
 * IEventSystem
 *
 * @author Santiago Barreiro
 */
public interface IEventSystem {

    /**
     * Registers an observer in this system. If the list is null it is considered to be an all listening observer
     * @param newObserver New observer to be registered
     * @param subscriptions List of enum values which the observer will be notified of
     */
     void attachObserver(Enum<?>[] subscriptions, IEvent.Observer newObserver);

    /**
     * Removes the observer from the specified subscriptions. If the list is null the observer should be detached from
     * all subscriptions
     * @param toDetach The observer to detach
     * @param subscriptions List of subscriptions to be detached from. If null all subscriptions will be detached
     */
     void detachObserver(Enum<?>[] subscriptions, IEvent.Observer toDetach);

    /**
     * Throws a new event into the event stack of this system
     */
    void throwEvent(IEvent event);

    /**
     * Handles the queued events
     */
    void dispatchEvents();
}

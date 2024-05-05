package esp.api;

/**
 * IObserver
 *
 * @author Santiago Barreiro
 */
public interface IObserver<T extends IEvent> {

    /**
     * Returns the list of enum values representing the subscriptions of this event
     * @return Array of enum values
     */
    Enum<?>[] getSubscriptions();

    /**
     * Method to be executed when notified of an event
     * @param event Notified IEvent instance
     */
    void onEvent(T event);
}

package esp.api;

import java.util.ArrayList;

/**
 * IObserver
 *
 * @author Santiago Barreiro
 */
public interface IObserver {

    /**
     * Method to be executed when notified of an event
     * @param event Notified IEvent instance
     */
    void handleEvent(IEvent event);
}

package frontend.events;

import backend.api.IEvent;
import backend.api.ITask;
import backend.api.ITaskStowage;

import static frontend.events.Event.Type.*;

/**
 * UIEventObserver
 *
 * @author Santiago Barreiro
 */
public class EventObserver implements IEvent.Observer {

    // ATTRIBUTES
    private final ITaskStowage.QueryMaker queryMaker;

    // CONSTRUCTORS
    public EventObserver(ITaskStowage.QueryMaker queryMaker) {
        this.queryMaker = queryMaker;
    }

    // METHODS
    @Override
    public void handleEvent(IEvent event) {
        if (event.getPayload() instanceof ITask) {
            if (event.getEventType() == STOW_TASK) queryMaker.stowTask(((ITask) event.getPayload()));
        }
    }
}
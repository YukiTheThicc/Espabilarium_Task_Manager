package frontend.events;

import backend.api.IEvent;
import backend.api.ITask;
import backend.tasks.TaskStowage;
import static frontend.events.Event.Type.*;

/**
 * UIEventObserver
 *
 * @author Santiago Barreiro
 */
public class EventObserver implements IEvent.Observer {

    // ATTRIBUTES
    private final TaskStowage targetStowage;

    // CONSTRUCTORS
    public EventObserver(TaskStowage targetStowage) {
        this.targetStowage = targetStowage;
    }

    // METHODS
    @Override
    public void handleEvent(IEvent event) {
        if (event.getPayload() instanceof ITask) {
            if (event.getEventType() == STOW_TASK) targetStowage.stowUpdateTask(((ITask) event.getPayload()));
        }
    }
}
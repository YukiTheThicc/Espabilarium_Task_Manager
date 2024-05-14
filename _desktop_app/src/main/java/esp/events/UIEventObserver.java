package esp.events;

import esp.api.IEvent;
import esp.api.ITask;
import esp.tasks.TaskStowage;

import static esp.events.UIEvent.Type.CREATE_TASK;
import static esp.events.UIEvent.Type.SAVE_TASK;

/**
 * UIEventObserver
 *
 * @author Santiago Barreiro
 */
public class UIEventObserver implements IEvent.Observer {

    // ATTRIBUTES
    private TaskStowage targetStowage;

    // CONSTRUCTORS
    public UIEventObserver(TaskStowage targetStowage) {
        this.targetStowage = targetStowage;
    }

    // METHODS
    @Override
    public void handleEvent(IEvent event) {
        if (event.getPayload() instanceof ITask) {
            if (event.getEventType() == CREATE_TASK) targetStowage.stowTask(((ITask) event.getPayload()));
            if (event.getEventType() == SAVE_TASK) targetStowage.updateTask(((ITask) event.getPayload()));
        }
    }
}
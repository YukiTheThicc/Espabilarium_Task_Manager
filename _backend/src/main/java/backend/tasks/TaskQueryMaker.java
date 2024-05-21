package backend.tasks;

import backend.api.ITask;
import backend.api.ITaskStowage;

import java.util.Collection;

/**
 * TaskQueryMaker
 *
 * @author Santiago Barreiro
 */
public class TaskQueryMaker implements ITaskStowage.QueryMaker {

    // ATTRIBUTES
    private TaskStowage stowage;

    // CONSTRUCTORS
    public TaskQueryMaker() {
        this.stowage = null;
    }

    // METHODS
    public void connectStowage(ITaskStowage stowage) {
        if (stowage instanceof TaskStowage) {
            this.stowage = (TaskStowage) stowage;
        }
    }

    @Override
    public Collection<ITask> queryTasks(String field, Order order) {
        if (stowage != null) {
            return stowage.getTasks();
        } else return null;
    }
}

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
    private ITaskStowage stowage;

    // CONSTRUCTORS
    public TaskQueryMaker() {
        this.stowage = null;
    }

    public TaskQueryMaker(ITaskStowage stowage) {
        this.stowage = stowage;
    }

    // METHODS
    public void connectStowage(ITaskStowage stowage) {
        if (stowage instanceof TaskStowage) {
            this.stowage = (TaskStowage) stowage;
        }
    }

    @Override
    public Collection<ITask> selectTasks(String field, SelectOrder order) {
        if (stowage != null) {
            return stowage.getAllTasks();
        } else return null;
    }

    @Override
    public void stowTask(ITask task) {
        stowage.stowTask(task);
    }

    @Override
    public void removeTask(String uuid) {

    }
}

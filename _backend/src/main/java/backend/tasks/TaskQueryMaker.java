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
    private final ITaskStowage stowage;

    // CONSTRUCTORS
    public TaskQueryMaker(ITaskStowage stowage) {
        this.stowage = stowage;
    }

    // METHODS
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

    public void updateTask(ITask task) {
        stowage.saveTask(task);
    }

    @Override
    public void removeTask(String uuid) {

    }
}

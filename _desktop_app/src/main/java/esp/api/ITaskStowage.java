package esp.api;

import esp.tasks.Task;

/**
 * ITaskStowage
 *
 * @author Santiago Barreiro
 */
public interface ITaskStowage {

    void stowTask(ITask task);

    void nestTask(ITask parent, ITask Child);

    ITask getTask(String taskID);
}

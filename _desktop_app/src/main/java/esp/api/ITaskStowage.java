package esp.api;

import esp.tasks.Task;

/**
 * ITaskStowage
 *
 * @author Santiago Barreiro
 */
public interface ITaskStowage {

    /**
     * Stows the passed task into the
     * @param task
     */
    void stowTask(ITask task);

    /**
     * Nests into the parent task (first one) the specified child task (second one)
     * @param parent The parent task
     * @param Child The child task
     */
    void nestTask(ITask parent, ITask Child);

    ITask getTask(String taskID);

    void saveTask(ITask task, String path);

    ITask loadTask(String path, String uuid);
}

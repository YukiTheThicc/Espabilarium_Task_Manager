package backend.api;

import java.util.Collection;

/**
 * ITaskStowage
 *
 * @author Santiago Barreiro
 */
public interface ITaskStowage {

    void stowUpdateTask(ITask updatedTask);

    /**
     * Nests into the parent task (first one) the specified child task (second one)
     * @param parent The parent task
     * @param Child The child task
     */
    void nestTask(ITask parent, ITask Child);

    /**
     * Fetches a single Task by directly fetching it through its ID
     * @param taskID UUID of the desired Task
     * @return The desired Task or null if not found
     */
    ITask getTask(String taskID);

    /**
     * Saves the specified Task on disk within the specified directory. The files name will be the same as the ID of the Task
     * @param task The Task to be saved
     * @param dir The directory to save the Task in. Can be relative
     */
    void saveTask(ITask task, String dir);

    /**
     * Loads one Task from a directory
     * @param dir Directory from which to load the file from
     * @param uuid UUID (name of the file) for the loaded Task
     * @return The loaded Task or null if something went wrong
     */
    ITask loadTask(String dir, String uuid);

    public interface QueryMaker {

        public enum Order {
            ASCENDENT,
            DESCENDANT
        }

        void connectStowage(ITaskStowage stowage);

        Collection<ITask> queryTasks(String field, Order order);
    }
}

package backend.api;

import java.util.Collection;

/**
 * ITaskStowage
 *
 * @author Santiago Barreiro
 */
public interface ITaskStowage {

    /**
     * Get the absolute path where task data files are being stored
     * @return The path String of the directory
     */
    String getDataDir();

    // >> CRUD METHODS
    /**
     * Attempts to stow the passed task within the system. Should not immediately save to disk the task
     * @param newTask Task to be stowed
     */
    void stowTask(ITask newTask);

    ITask loadTask(String dataPath);

    void saveTask(ITask task);

    void archiveTask(ITask toArchive);
    // << CRUD METHODS

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
     * Retrieves all Tasks stored in memory within this system
     * @return Collection with all stored tasks
     */
    Collection<ITask> getAllTasks();

    interface QueryMaker {

        public enum SelectOrder {
            ASCENDENT,
            DESCENDANT
        }

        Collection<ITask> selectTasks(String field, SelectOrder order);

        void stowTask(ITask task);

        void updateTask(ITask task);

        void removeTask(String uuid);
    }
}

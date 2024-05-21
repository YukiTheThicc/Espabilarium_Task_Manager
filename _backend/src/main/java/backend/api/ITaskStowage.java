package backend.api;

import java.util.Collection;

/**
 * ITaskStowage
 *
 * @author Santiago Barreiro
 */
public interface ITaskStowage {

    void stowTask(ITask newTask);

    void updateTask(ITask updatedTask);

    /**
     * Nests into the parent task (first one) the specified child task (second one)
     * @param parent The parent task
     * @param Child The child task
     */
    void nestTask(ITask parent, ITask Child);

    /**
     * Retrieves all Tasks stored in memory within this system
     * @return Collection with all stored tasks
     */
    Collection<ITask> getAllTasks();

    /**
     * Fetches a single Task by directly fetching it through its ID
     * @param taskID UUID of the desired Task
     * @return The desired Task or null if not found
     */
    ITask getTask(String taskID);

    public interface QueryMaker {

        public enum SelectOrder {
            ASCENDENT,
            DESCENDANT
        }

        Collection<ITask> selectTasks(String field, SelectOrder order);

        void stowTask(ITask task);

        void removeTask(String uuid);
    }
}

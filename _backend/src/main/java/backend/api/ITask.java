package backend.api;

import backend.tasks.TaskPriority;
import backend.tasks.TaskState;
import backend.tasks.TaskType;

import java.util.ArrayList;

/**
 * ITask
 *
 * @author Santiago Barreiro
 */
public interface ITask {

    // GETTERS & SETTERS
    String getUuid();
    String getName();
    ITask getParent();
    TaskType getType();
    TaskState getState();
    TaskPriority getPriority();
    float getProgress();
    void setName(String name);
    void setParent(ITask parent);
    void setType(TaskType type);
    void setState(TaskState state);
    void setPriority(TaskPriority priority);
    void setProgress(float progress);

    // METHODS
    ArrayList<ITask> getChildren();
    void addChild(ITask newChild);
    void removeChild(ITask toRemove);

    ITask copy(String uuid);
}

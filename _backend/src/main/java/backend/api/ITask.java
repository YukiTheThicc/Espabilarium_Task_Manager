package backend.api;

import java.util.ArrayList;
import java.util.Date;

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

    Enum<?> getType();

    Enum<?> getState();

    Enum<?> getPriority();

    float getProgress();

    void setName(String name);

    void setParent(ITask parent);

    void setType(Enum<?> type);

    void setState(Enum<?> state);

    void setPriority(Enum<?> priority);

    void setProgress(float progress);

    // METHODS
    void addChangeLog(Date date, String message);

    void addComponent(int hash, Object newComponent);

    Object getComponent(int hash);

    void removeComponent(int hash);

    ArrayList<ITask> getChildren();

    void addChild(ITask newChild);

    void removeChild(ITask toRemove);

    ITask copy(String uuid);

    interface Component {

    }

    /**
     * Interface to mask objects returning the data of loaded tasks to be the inserted, sorted and indexed by the target
     * ITaskStowage
     */
    interface TaskData {
        ITask getTask();

        Component[] getComponents();

        String[] getChildrenIDs();
    }
}

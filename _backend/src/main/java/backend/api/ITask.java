package backend.api;

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
    void addComponent(Component newComponent);

    void removeComponent(Component toRemove);

    ArrayList<ITask> getChildren();

    void addChild(ITask newChild);

    void removeChild(ITask toRemove);

    ITask copy(String uuid);

    interface Component {
        void copy(Component target);
    }
}

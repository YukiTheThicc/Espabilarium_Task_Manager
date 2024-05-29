package backend.tasks;

import backend.api.ITask;
import backend.exceptions.EspRuntimeException;
import backend.utils.EspUUID;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Task
 *
 * @author Santiago Barreiro
 */
public class Task implements ITask {

    public enum Type {

        TASK("task"),
        IDEA("idea"),
        PROJECT("project");

        private final String tag;

        Type(String type) {
            this.tag = type;
        }
    }

    public enum State {

        NEW("new", 0),
        ON_HOLD("on_hold", 1),
        IN_PROGRESS("in_progress", 2),
        RESOLVED("resolved", 3),
        FINISHED("finished", 4),
        ARCHIVED("archived", 4);

        private final String tag;

        State(String literal, int code) {
            this.tag = literal;
        }
    }

    public enum Priority {

        HIGHEST,
        LOWEST
    }

    public static class TaskFactory {
        public static Task createTask(String name) {
            return new Task(EspUUID.generateUUID(), name, Type.TASK, Priority.LOWEST);
        }
    }

    // ATTRIBUTES
    private final String uuid;
    private String name;
    private float progress;
    private Type type;
    private State state;
    private Priority priority;
    private final ArrayList<ITask> children;

    // RUNTIME ATTRIBUTES
    private transient ITask parent;

    // CONSTRUCTORS
    public Task(String uuid, String name, Type type, Priority priority) {
        this.uuid = uuid;
        this.name = name;
        this.parent = null;
        this.progress = 0;
        this.type = type;
        this.state = State.NEW;
        this.priority = priority;
        this.children = new ArrayList<>();
    }

    // GETTERS & SETTERS
    public String getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ITask getParent() {
        return parent;
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public Enum<?> getType() {
        return type;
    }

    @Override
    public Enum<?> getState() {
        return state;
    }

    @Override
    public Enum<?> getPriority() {
        return priority;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setParent(ITask parent) {
        this.parent = parent;
    }

    @Override
    public void setProgress(float progress) {
        if (progress < 0) this.progress = 0f;
        else if (progress > 1) this.progress = 1f;
        else this.progress = progress;
    }

    @Override
    public void setType(Enum<?> type) {
        this.type = (Type) type;
    }

    @Override
    public void setState(Enum<?> state) {
        this.state = (State) state;
    }

    @Override
    public void setPriority(Enum<?> priority) {
        this.priority = (Priority) priority;
    }

    @Override
    public ArrayList<ITask> getChildren() {
        return this.children;
    }

    public Object getField(Field field) {
        try {
            return field.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // METHODS

    @Override
    public void addComponent(Component newComponent) {

    }

    @Override
    public void removeComponent(Component toRemove) {

    }

    @Override
    public void addChild(ITask newChild) {
        if (newChild == null) throw new EspRuntimeException("Tried to add null child");
        if (children.contains(newChild))
            throw new EspRuntimeException("Tried to add a child with an already existing UUID");
        if (newChild.getUuid().equals(this.uuid))
            throw new EspRuntimeException("Tried to set task as children of itself");
        this.children.add(newChild);
    }

    @Override
    public void removeChild(ITask toRemove) {
        children.remove(toRemove);
    }

    @Override
    public ITask copy(String uuid) {
        ITask copied = new Task(uuid, this.name, this.type, this.priority);
        copied.setProgress(this.getProgress());
        copied.setType(this.getType());
        copied.setState(this.getState());
        copied.setParent(this.getParent());
        for (ITask child : this.getChildren()) {
            copied.addChild(child);
        }
        return copied;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ITask)) return false;
        return this.uuid.equals(((ITask) obj).getUuid());
    }

    @Override
    public String toString() {
        String parent = this.parent != null ? this.parent.getUuid() + " | " + this.parent.getName() : "-";
        return "\nTask : { " +
                "\n\t UUID: " + uuid +
                "\n\t name: " + name +
                "\n\t Parent: " + parent +
                "\n\t progress: " + progress +
                "\n\t type: " + type +
                "\n\t state: " + state +
                "\n\t priority: " + priority +
                "\n}\n";
    }
}

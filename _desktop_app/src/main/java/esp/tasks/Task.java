package esp.tasks;

import esp.api.ITask;
import esp.exceptions.EspRuntimeException;
import esp.utils.EspUUID;
import esp.utils.Resources;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Task
 *
 * @author Santiago Barreiro
 */
public class Task implements ITask {

    public static class TaskFactory {
        public static Task createTask() {
            return new Task(EspUUID.generateUUID(), Resources.literal("new_task"), TaskType.TASK, TaskPriority.LOWEST);
        }
    }

    // ATTRIBUTES
    private final String uuid;
    private String name;
    private float progress;
    private TaskType type;
    private TaskState state;
    private TaskPriority priority;
    private final ArrayList<ITask> children;

    // RUNTIME ATTRIBUTES
    private transient ITask parent;

    // CONSTRUCTORS
    public Task(String uuid, String name, TaskType type, TaskPriority priority) {
        this.uuid = uuid;
        this.name = name;
        this.parent = null;
        this.progress = 0;
        this.type = type;
        this.state = TaskState.NEW;
        this.priority = priority;
        this.children = new ArrayList<>();
    }

    // GETTERS & SETTERS
    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ITask getParent() {
        return parent;
    }

    public float getProgress() {
        return this.progress;
    }

    public TaskType getType() {
        return type;
    }

    public TaskState getState() {
        return state;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(ITask parent) {
        this.parent = parent;
    }

    public void setProgress(float progress) {
        if (progress < 0) this.progress = 0f;
        else if (progress > 1) this.progress = 1f;
        else this.progress = progress;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public Object getField(Field field) {
        try {
            return field.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ITask> getChildren() {
        return this.children;
    }

    // METHODS

    public void addChild(ITask newChild) {
        if (newChild == null) throw new EspRuntimeException("Tried to add null child");
        if (children.contains(newChild))
            throw new EspRuntimeException("Tried to add a child with an already existing UUID");
        if (newChild.getUuid().equals(this.uuid))
            throw new EspRuntimeException("Tried to set task as children of itself");
        this.children.add(newChild);
    }

    public void removeChild(ITask toRemove) {
        children.remove(toRemove);
    }

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

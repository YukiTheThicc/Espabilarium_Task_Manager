package espbilarium.tasks;

import espbilarium.utils.EspUUID;

/**
 * Task
 *
 * @author Santiago Barreiro
 */
public final class Task {

    // ATTRIBUTES
    private final String uuid;
    private final TaskType type;
    private String name;
    private Task parent;

    // CONSTRUCTORS
    public Task(String name, TaskType type) {
        this.uuid = EspUUID.generateUUID();
        this.name = name;
        this.parent = null;
        this.type = type;
    }

    // GETTERS & SETTERS
    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Task getParent() {
        return parent;
    }

    public TaskType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }

    // METHODS
}

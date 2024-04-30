package esp.tasks;

import esp.api.ITask;
import esp.api.ITaskStowage;
import esp.exceptions.EspRuntimeException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TaskStowage
 *
 * @author Santiago Barreiro
 */
public class TaskStowage implements ITaskStowage {

    // ATTRIBUTES
    private final HashMap<String, ITask> tasks;

    // CONSTRUCTORS
    public TaskStowage() {
        this.tasks = new HashMap<>();
    }

    // GETTERS & SETTERS


    // METHODS
    public void stowTask(ITask newTask) {
        if (newTask == null) throw new EspRuntimeException("Tried to add a null task");
        if (tasks.get(newTask.getUuid()) != null)
            throw new EspRuntimeException("Tried to insert a Task with an already existing UUID");
        tasks.put(newTask.getUuid(), newTask);
    }

    public void nestTask(ITask parent, ITask child) {
        if (child == null || tasks.get(child.getUuid()) == null)
            throw new EspRuntimeException("Tried to nest a null or not stowed child");
        if (parent != null && tasks.get(parent.getUuid()) != null) {
            // Remove child form previous parent child list
            if (child.getParent() != null) {
                parent.removeChild(child);
            }
            // Set new parent
            child.setParent(parent);
            parent.addChild(child);
        } else {
            child.setParent(null);
        }
    }

    public ITask getTask(String taskID) {
        return tasks.get(taskID);
    }
}

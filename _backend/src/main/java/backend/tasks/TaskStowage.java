package backend.tasks;

import backend.api.IEventSystem;
import backend.api.ITask;
import backend.api.ITaskStowage;
import backend.events.Event;
import backend.exceptions.EspRuntimeException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

/**
 * TaskStowage
 *
 * @author Santiago Barreiro
 */
public class TaskStowage implements ITaskStowage {

    // ATTRIBUTES
    private final String dataDir;
    private final HashMap<String, ITask> tasks;
    private final IEventSystem es;
    private Serializer serializer;

    // CONSTRUCTORS
    public TaskStowage(IEventSystem es, String dataDir) {
        this.dataDir = dataDir;
        this.tasks = new HashMap<>();
        this.es = es;
    }

    @Override
    public String getDataDir() {
        return dataDir;
    }

    @Override
    public ITaskStowage setSerializer(Serializer newSerializer) {
        this.serializer = newSerializer;
        return this;
    }

    // METHODS

    // >> CRUD METHODS
    @Override
    public void stowTask(ITask task) {
        if (task == null) throw new EspRuntimeException("Tried to stow null task");
        tasks.putIfAbsent(task.getUuid(), task);
    }

    @Override
    public ITask loadTask(String path) {
        ITask loaded = null;
        if (serializer != null) loaded = serializer.deserialize(path);
        if (loaded != null) stowTask(loaded);
        return loaded;
    }

    @Override
    public boolean saveTask(String dir, ITask task) {
        boolean success = false;
        if (serializer != null) success = serializer.serialize(dir, task);
        if (!success) es.throwEvent(new Event(Event.Type.SAVED_TASK, task.getUuid()));
        return success;
    }

    @Override
    public void archiveTask(ITask toArchive) {

    }
    // << CRUD METHODS

    @Override
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
        es.throwEvent(new Event(Event.Type.SAVED_TASK, child.getUuid()));
    }

    @Override
    public ITask getTask(String taskID) {
        return tasks.get(taskID);
    }

    public Collection<ITask> getAllTasks() {
        return tasks.values();
    }
}

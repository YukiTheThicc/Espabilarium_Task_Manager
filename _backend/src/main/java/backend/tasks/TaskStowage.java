package backend.tasks;

import backend.api.IEventSystem;
import backend.api.ITask;
import backend.api.ITaskStowage;
import backend.events.Event;
import backend.events.EventSystem;
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

    // CONSTANTS
    private static final String DEFAULT_DATA_FOLDER = System.getProperty("user.dir") + "\\data\\";
    private static final int DEFAULT_THREADS = 4;

    // ATTRIBUTES
    private final HashMap<String, ITask> tasks;
    private final IEventSystem es;

    // CONSTRUCTORS
    public TaskStowage(EventSystem es) {
        this.tasks = new HashMap<>();
        this.es = es;
    }

    // GETTERS & SETTERS
    public Collection<ITask> getTasks() {
        return tasks.values();
    }

    // METHODS
    public void stowUpdateTask(ITask task) {
        if (task == null) throw new EspRuntimeException("Tried to update null task");
        tasks.putIfAbsent(task.getUuid(), task);
        saveTask(task, DEFAULT_DATA_FOLDER);
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
        es.throwEvent(new Event(Event.Type.SAVED_TASK, child.getUuid()));
    }

    public ITask getTask(String taskID) {
        return tasks.get(taskID);
    }

    public void saveTask(ITask task, String dataPath) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .create();
        try {
            File targetFile = new File(dataPath + task.getUuid() + ".json");
            if (targetFile.createNewFile()) {
                FileWriter writer = new FileWriter(targetFile.getAbsoluteFile());
                writer.write(gson.toJson(task));
                writer.close();
                es.throwEvent(new Event(Event.Type.SAVED_TASK, task.getUuid()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ITask loadTask(String dataPath, String uuid) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(ITask.class, new TaskSerializer())
                .create();
        String inFile = "";
        ITask loaded = null;
        try {
            inFile = new String(Files.readAllBytes(Paths.get(dataPath + uuid + ".json")));
            if (!inFile.equals("")) {
                loaded = gson.fromJson(inFile, Task.class);
            }
        } catch (Exception e) {
            return null;
        }
        return loaded;
    }
}

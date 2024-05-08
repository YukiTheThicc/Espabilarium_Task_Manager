package esp.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import esp.api.IEvent;
import esp.api.ITask;
import esp.api.ITaskStowage;
import esp.exceptions.EspRuntimeException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

import static esp.events.Event.Type.CREATE_TASK;

/**
 * TaskStowage
 *
 * @author Santiago Barreiro
 */
public class TaskStowage implements ITaskStowage, IEvent.Observer {

    // CONSTANTS
    private static final String DEFAULT_DATA_FOLDER = "\\data";

    // ATTRIBUTES
    private final HashMap<String, ITask> tasks;

    // CONSTRUCTORS
    public TaskStowage() {
        this.tasks = new HashMap<>();
    }

    // GETTERS & SETTERS
    public Collection<ITask> getTasks() {
        return tasks.values();
    }

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

    public void saveTask(ITask task, String dataPath) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .create();
        try {
            File yourFile = new File(dataPath + task.getUuid() + ".json");
            if (yourFile.createNewFile()) {
                FileWriter writer = new FileWriter(yourFile.getAbsoluteFile());
                writer.write(gson.toJson(task));
                writer.close();
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

    @Override
    public void handleEvent(IEvent event) {
        if (event.getEventType().equals(CREATE_TASK)) {
            System.out.println("Created task");
            stowTask(Task.TaskFactory.createTask());
        }
    }
}

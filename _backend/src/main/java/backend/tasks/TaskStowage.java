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

    // METHODS

    // >> CRUD METHODS
    @Override
    public void stowTask(ITask task) {
        if (task == null) throw new EspRuntimeException("Tried to stow null task");
        tasks.putIfAbsent(task.getUuid(), task);
    }

    @Override
    public ITask loadTask(String dataPath) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(ITask.class, new TaskSerializer())
                .create();
        String inFile;
        ITask loaded = null;
        try {
            inFile = new String(Files.readAllBytes(Paths.get(dataPath)));
            if (!inFile.equals("")) {
                loaded = gson.fromJson(inFile, Task.class);
            }
        } catch (Exception e) {
            return null;
        }
        stowTask(loaded);
        return loaded;
    }

    @Override
    public void saveTask(ITask task) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .create();
        try {
            File directory = new File(dataDir);
            if (!directory.exists()) directory.mkdir();
            if (directory.isDirectory()) {
                File targetFile = new File(dataDir + task.getUuid() + ".json");
                targetFile.createNewFile();
                if (targetFile.isFile()) {
                    FileWriter writer = new FileWriter(targetFile.getAbsoluteFile());
                    writer.write(gson.toJson(task));
                    writer.close();
                    es.throwEvent(new Event(Event.Type.SAVED_TASK, task.getUuid()));
                } else {
                    throw new EspRuntimeException("Could not find nor create the dataPath to save data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

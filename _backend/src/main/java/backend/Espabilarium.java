package backend;

import backend.api.IEspabilarium;
import backend.api.IEventSystem;
import backend.api.ITaskStowage;
import backend.events.Event;
import backend.events.EventSystem;
import backend.tasks.TaskQueryMaker;
import backend.tasks.TaskStowage;
import backend.utils.EspLogger;
import backend.utils.Utils;
import org.graalvm.nativeimage.hosted.RuntimeJNIAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Espabilarium
 *
 * @author Santiago Barreiro
 */
public class Espabilarium implements IEspabilarium {

    // ATTRIBUTES
    private final TaskStowage stowage;
    private final IEventSystem eventSystem;
    private final NotificationService notificationService;
    private ScheduledExecutorService exec;

    // CONSTRUCTORS
    public Espabilarium() {
        this.eventSystem = new EventSystem();
        this.stowage = new TaskStowage(this.eventSystem);
        this.notificationService = new NotificationService();
    }

    // GETTERS & SETTERS
    public IEventSystem getEventSystem() {
        return this.eventSystem;
    }

    // METHODS

    /**
     * Initializes the app and storage, setting up the notification service and loading into memory all stored data
     * @param dataFolder Directory to load the data form
     */
    public void init(String dataFolder) {
        // Launch background service
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(notificationService, 0, 2, TimeUnit.SECONDS);
        loadData(dataFolder);
    }

    public void loadData(String dataPath) {
        Runnable loader = () -> {
            File dataDir = new File(dataPath == null ? TaskStowage.DEFAULT_DATA_FOLDER : dataPath);
            if (dataDir.isDirectory()) {
                ArrayList<File> taskFiles = Utils.getFilesInDir(dataDir.getAbsolutePath(), "json");
                for (File file : taskFiles) {
                    stowage.loadTask(file.getAbsolutePath());
                }
            }
            EspLogger.log("Finished loading tasks from data dir");
            eventSystem.throwEvent(new Event(Event.Type.LOADED_TASKS));
        };
        loader.run();
    }

    public void close() {
        exec.shutdown();
    }

    public ITaskStowage.QueryMaker queryMaker() {
        return new TaskQueryMaker(this.stowage);
    }
}

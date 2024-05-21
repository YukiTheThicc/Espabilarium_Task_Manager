package backend;

import backend.api.IEspabilarium;
import backend.api.IEventSystem;
import backend.api.ITaskStowage;
import backend.events.EventSystem;
import backend.tasks.TaskQueryMaker;
import backend.tasks.TaskStowage;
import backend.utils.Utils;

import java.io.File;
import java.nio.file.Files;
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

        File dataDir = new File(dataFolder == null ? TaskStowage.DEFAULT_DATA_FOLDER : dataFolder);
        if (dataDir.isDirectory()) {
            ArrayList<File> taskFiles = Utils.getFilesInDir(dataDir.getAbsolutePath(), "json");
            for (File file : taskFiles) {
                System.out.println(file.getAbsoluteFile());
                System.out.println(file.getName());
                stowage.loadTask(file.getAbsolutePath(), file.getName().split("\\.")[0]);
            }
        }
    }

    public void close() {
        exec.shutdown();
    }

    public ITaskStowage.QueryMaker queryMaker() {
        return new TaskQueryMaker(this.stowage);
    }
}

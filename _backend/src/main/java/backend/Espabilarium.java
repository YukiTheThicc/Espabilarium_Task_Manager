package backend;

import backend.api.IEspabilarium;
import backend.api.IEventSystem;
import backend.api.ITaskStowage;
import backend.events.EventSystem;
import backend.tasks.TaskQueryMaker;
import backend.tasks.TaskStowage;

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
    private final ITaskStowage stowage;
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
    public void init() {
        // Launch background service
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(notificationService, 0, 2, TimeUnit.SECONDS);
    }

    public void close() {
        exec.shutdown();
    }

    public ITaskStowage.QueryMaker queryMaker() {
        return new TaskQueryMaker(this.stowage);
    }
}

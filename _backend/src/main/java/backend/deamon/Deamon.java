package backend.deamon;

import backend.api.IEventSystem;
import backend.api.INotifier;
import backend.api.ITask;
import backend.api.ITaskStowage;
import backend.utils.EspLogger;

/**
 * NotificationService
 *
 * @author Santiago Barreiro
 */
public class Deamon implements Runnable {

    // CONSTANTS

    // ATTRIBUTES
    private final ITaskStowage stowage;
    private final IEventSystem es;
    private final INotifier notifier;
    private int runs = 1;
    private boolean firstRun = true;

    // CONSTRUCTORS
    public Deamon(ITaskStowage stowage, IEventSystem es, INotifier notifier) {
        this.stowage = stowage;
        this.es = es;
        this.notifier = notifier;
    }

    // METHODS
    @Override
    public void run() {
        EspLogger.log("Checking for notifications... " + runs);
        es.dispatchEvents();
        runs++;
        if (firstRun) {
            notifier.notifyUser("There are [" + stowage.getAllTasks().size() + "] active tasks");
            firstRun = false;
        }
    }
}

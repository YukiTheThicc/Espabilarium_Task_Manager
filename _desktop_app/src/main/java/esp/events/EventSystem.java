package esp.events;

import esp.api.IEvent;
import esp.api.IObserver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * EventHandler
 *
 * @author Santiago Barreiro
 */
public class EventSystem {

    // ATTRIBUTES
    private final ArrayList<IEvent> eventStack;
    private final HashMap<Enum<?>, ArrayList<IObserver>> observers;

    // CONSTRUCTORS
    public EventSystem() {
        this.eventStack = new ArrayList<>();
        this.observers = new HashMap<>();
    }

    // METHODS
    public void addObserver(IObserver observer, Enum<?>[] subscriptions) {
        if (observer != null) {
            for (Enum<?> subscription : subscriptions) {
                observers.computeIfAbsent(subscription, k -> new ArrayList<>());
                observers.get(subscription).add(observer);
            }
        }
    }

    public void throwEvent(IEvent event) {
        if (event != null) {
            eventStack.add(event);
        }
    }

    public void handleEvents() {
        int handleIterations;
        for (IEvent event : eventStack) {
            handleIterations = 0;
            ArrayList<IObserver> typeObservers = observers.get(event.getEventType());
            for (IObserver observer : typeObservers) {
                if (event.getHandleIterations() == handleIterations) break;
                observer.handleEvent(event);
                handleIterations ++;
            }
        }
    }
}

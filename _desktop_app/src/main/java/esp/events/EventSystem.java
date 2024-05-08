package esp.events;

import esp.api.IEvent;
import esp.api.IEventSystem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * EventHandler
 *
 * @author Santiago Barreiro
 */
public class EventSystem implements IEventSystem {

    // ATTRIBUTES
    private final ArrayList<IEvent> eventStack;
    private final HashMap<Enum<?>, ArrayList<IEvent.Observer>> observers;

    // CONSTRUCTORS
    public EventSystem() {
        this.eventStack = new ArrayList<>();
        this.observers = new HashMap<>();
    }

    // METHODS
    public void addObserver(IEvent.Observer observer, Enum<?>[] subscriptions) {
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

    public void dispatchEvents() {
        int handleIterations;
        for (IEvent event : eventStack) {
            handleIterations = 0;
            ArrayList<IEvent.Observer> typeObservers = observers.get(event.getEventType());
            if (typeObservers != null) {
                for (IEvent.Observer observer : typeObservers) {
                    if (event.getHandleIterations() == handleIterations) break;
                    observer.handleEvent(event);
                    handleIterations++;
                }
            }
        }
        eventStack.clear();
    }
}

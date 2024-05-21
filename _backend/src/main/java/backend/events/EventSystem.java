package backend.events;

import backend.api.IEvent;
import backend.api.IEventSystem;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * EventHandler
 *
 * @author Santiago Barreiro
 */
public class EventSystem implements IEventSystem {

    // ATTRIBUTES
    private final ArrayList<IEvent> eventStack;                                 // Stack of events to dispatch
    private final ArrayList<IEvent> eventBuffer;                                // Buffer of events that couldn't be dispatched
    private final HashMap<Enum<?>, ArrayList<IEvent.Observer>> typeObservers;   // Observers by type
    private final HashMap<String, ArrayList<IEvent.Observer>> objObservers;     // Observers by type
    private boolean isDispatching = false;                                      // Flag to indicate if the system is dispatching events

    // CONSTRUCTORS
    public EventSystem() {
        this.eventStack = new ArrayList<>();
        this.eventBuffer = new ArrayList<>();
        this.typeObservers = new HashMap<>();
        this.objObservers = new HashMap<>();
    }

    // METHODS
    @Override
    public void attachObserver(Enum<?>[] subscriptions, IEvent.Observer observer) {
        if (observer != null) {
            for (Enum<?> subscription : subscriptions) {
                typeObservers.computeIfAbsent(subscription, k -> new ArrayList<>());
                typeObservers.get(subscription).add(observer);
            }
        }
    }

    @Override
    public void attachObserver(String subscription, IEvent.Observer observer) {
        if (observer != null) {
            objObservers.computeIfAbsent(subscription, k -> new ArrayList<>());
            objObservers.get(subscription).add(observer);
        }
    }

    @Override
    public void detachObserver(Enum<?>[] subscriptions, IEvent.Observer toDetach) {
        if (toDetach != null) {
            for (Enum<?> subscription : subscriptions) {
                ArrayList<IEvent.Observer> observers = typeObservers.get(subscription);
                if (observers != null) observers.remove(toDetach);
            }
        }
    }

    @Override
    public void detachObserver(String subscription, IEvent.Observer toDetach) {
        if (toDetach != null) {
            ArrayList<IEvent.Observer> observers = objObservers.get(subscription);
            if (observers != null) observers.remove(toDetach);
        }
    }

    @Override
    public void throwEvent(IEvent event) {
        if (event != null) {
            if (isDispatching) eventBuffer.add(event);
            else eventStack.add(event);
            System.out.println(event.getEventType());
        }
    }

    @Override
    public void dispatchEvents() {
        int handleIterations;
        isDispatching = true;

        // Dispatch general type events
        for (IEvent event : eventStack) {
            handleIterations = 0;
            ArrayList<IEvent.Observer> observers = typeObservers.get(event.getEventType());
            if (observers != null) {
                for (IEvent.Observer observer : observers) {
                    if (event.getHandleIterations() == handleIterations) break;
                    observer.handleEvent(event);
                    handleIterations++;
                }
            }
        }

        // Finish dispatching
        isDispatching = false;
        eventStack.clear();

        // Collect all events that might have been thrown while dispatching
        eventStack.addAll(eventBuffer);
        eventBuffer.clear();
    }
}

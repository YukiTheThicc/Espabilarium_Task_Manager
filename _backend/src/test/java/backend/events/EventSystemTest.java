package backend.events;

import backend.api.IEvent;
import backend.events.EventSystem;
import backend.utils.EspLogger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventSystemTest {

    public enum TestType {
        TYPE1,
        TYPE2,
    }

    static class TestEvent implements IEvent {

        TestType type;
        boolean isHandled = false;
        int handleIterations;

        TestEvent(TestType type, int handleIterations) {
            this.type = type;
            this.handleIterations = handleIterations;
        }

        @Override
        public int getHandleIterations() {
            return handleIterations;
        }

        @Override
        public Enum<?> getEventType() {
            return type;
        }

        @Override
        public Object getPayload() {
            return "payload";
        }
    }

    static class TestObserver implements IEvent.Observer {

        public int handleCount = 0;

        @Override
        public void handleEvent(IEvent event) {
            EspLogger.log(event.getPayload().toString());
            handleCount++;
        }
    }

    EventSystem sut = new EventSystem();

    @Test
    void testHandleEvents() {
        TestObserver obs1 = new TestObserver();
        TestObserver obs2 = new TestObserver();
        TestObserver obs3 = new TestObserver();

        sut.throwEvent(new TestEvent(TestType.TYPE1, 1));
        sut.throwEvent(new TestEvent(TestType.TYPE2, 2));
        sut.throwEvent(new TestEvent(TestType.TYPE2, 2));
        sut.throwEvent(new TestEvent(TestType.TYPE2, 1));

        sut.dispatchEvents();

        assertAll(
                () -> assertEquals(1, obs1.handleCount),
                () -> assertEquals(3, obs2.handleCount),
                () -> assertEquals(2, obs3.handleCount)
        );
    }
}
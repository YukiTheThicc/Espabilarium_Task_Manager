package esp.events;

import esp.api.IEvent;
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

        @Override
        public boolean isHandled() {
            return isHandled;
        }

        @Override
        public void setHandled() {
            this.isHandled = true;
        }
    }

    static class TestObserver implements IEvent.Observer {

        public int handleCount = 0;

        @Override
        public void handleEvent(IEvent event) {
            System.out.println(event.getPayload().toString());
            handleCount++;
        }
    }

    EventSystem sut = new EventSystem();

    @Test
    void testHandleEvents() {
        TestObserver obs1 = new TestObserver();
        TestObserver obs2 = new TestObserver();
        TestObserver obs3 = new TestObserver();
        sut.addObserver(obs1, new Enum<?>[]{TestType.TYPE1});
        sut.addObserver(obs2, new Enum<?>[]{TestType.TYPE2});
        sut.addObserver(obs3, new Enum<?>[]{TestType.TYPE2});

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
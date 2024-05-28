package backend;

import backend.api.INotifier;
import backend.utils.EspLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class EspabilariumTest {

    Espabilarium sut;

    @BeforeEach
    void setUp() {
        sut = new Espabilarium(new INotifier() {
            @Override
            public void notifyUser(String message) {
                System.out.println(message);
            }

            @Override
            public void warnUser(String message) {
                System.out.println(message);
            }
        });
    }

    @Test
    void testRun() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                EspLogger.log("Updating");
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
        while(true);
    }
}
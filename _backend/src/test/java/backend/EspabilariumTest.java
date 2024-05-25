package backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class EspabilariumTest {

    Espabilarium sut;

    @BeforeEach
    void setUp() {
        sut = new Espabilarium();
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
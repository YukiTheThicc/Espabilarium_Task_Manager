package backend.tasks.serializers;

import backend.api.ITask;
import backend.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testUtils.TestComp;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TaskSerializerYAMLTest {

    TaskSerializerYAML sut = new TaskSerializerYAML();
    ITask task1;
    String testDataPath = new File("src/test/resources").getAbsolutePath() + "\\";

    @BeforeEach
    void setUp() {
        task1 = new Task("00000001", "Task 1", Task.Type.TASK, Task.Priority.LOWEST);
        task1.addComponent(1, new TestComp("15/06/2010-17:00"));
    }

    @Test
    void testSerialize() {
        assertTrue(sut.serialize(testDataPath, task1));
    }

    @Test
    void testDeserialize() {
        ITask loaded = sut.deserialize(testDataPath + "00000001.json");
        assertAll(() -> {
            assertEquals("00000001", loaded.getUuid());
            assertEquals("Task 1", loaded.getName());
            assertEquals(TestComp.format.parse("15/06/2010-17:00"), loaded.getComponent(1));
        });
    }
}
package backend.tasks.serializers;

import backend.api.ITask;
import backend.tasks.Task;
import testUtils.TestComp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TaskSerializerJSONTest {

    TaskSerializerJSON sut = new TaskSerializerJSON();
    ITask task1;
    ITask task2;
    String testDataPath = new File("src/test/resources").getAbsolutePath() + "\\";

    @BeforeEach
    void setUp() {
        task1 = new Task("00000001", "Task 1", Task.Type.TASK, Task.Priority.LOWEST);
        task2 = new Task("00000002", "Task 1 Child", Task.Type.TASK, Task.Priority.LOWEST);
        task1.addComponent(1, new TestComp("15/06/2010-17:00"));
        task2.addComponent(1, new TestComp("01/01/1999-00:00"));
        task1.addChild(task2);
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
            assertEquals(TestComp.format.parse("15/06/2010-17:00"), ((TestComp) loaded.getComponent(1)).getDate());
        });
    }
}
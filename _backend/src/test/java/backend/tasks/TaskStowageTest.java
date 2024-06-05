package backend.tasks;

import backend.api.ITask;
import backend.api.ITaskStowage;
import backend.events.EventSystem;
import backend.exceptions.EspRuntimeException;
import backend.tasks.serializers.TaskSerializerYAML;
import backend.utils.EspLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testUtils.TestComp;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskStowageTest {

    TaskStowage sut;
    ITask task1 = new Task("00000001", "Task 1", Task.Type.TASK, Task.Priority.LOWEST);
    ITask task2 = new Task("00000002", "Task 2", Task.Type.IDEA, Task.Priority.LOWEST);
    ITask task3 = new Task("00000003", "Task 3", Task.Type.PROJECT, Task.Priority.HIGHEST);
    String testDataPath = new File("src/test/resources").getAbsolutePath() + "\\";

    @BeforeEach
    void setUp() {
        sut = new TaskStowage(new EventSystem(), testDataPath);
        sut.setSerializer(new ITaskStowage.Serializer() {
            @Override
            public boolean serialize(String dir, ITask task) {
                return true;
            }

            @Override
            public ITask deserialize(String file) {
                return new Task("00000003", "Loaded", Task.Type.PROJECT, Task.Priority.HIGHEST);
            }
        });
    }

    @Test
    void testStowTask() {
        sut.stowTask(task1);
        sut.stowTask(task2);
        sut.stowTask(task3);
        assertThrows(EspRuntimeException.class, () -> sut.stowTask(null));
    }

    @Test
    void testLoadTask() {
        ITask loaded = sut.loadTask(testDataPath + "LOAD_TEST.json");
        assertAll(() -> {
            assertEquals("00000003", loaded.getUuid());
            assertEquals("Loaded", loaded.getName());
            assertEquals(Task.Type.PROJECT, loaded.getType());
            assertEquals(Task.Priority.HIGHEST, loaded.getPriority());
        });
    }

    @Test
    void testSaveTask() {
        sut.stowTask(task1);
        sut.stowTask(task2);
        task1.setName("Saved");
        task1.addComponent(1, new TestComp("15/06/2010-17:00"));
        sut.nestTask(task1, task2);
        assertTrue(sut.saveTask(testDataPath, task1));
    }

    @Test
    void testGetTask() {
        sut.stowTask(task1);
        sut.stowTask(task2);
        sut.stowTask(task3);
        ITask actual = sut.getTask("00000002");
        assertAll(() -> {
            assertEquals("00000002", actual.getUuid());
            assertEquals("Task 2", actual.getName());
            assertEquals(Task.Type.IDEA, actual.getType());
            assertEquals(Task.Priority.LOWEST, actual.getPriority());
        });
    }

    @Test
    void testNestTask() {
        sut.stowTask(task1);
        sut.stowTask(task2);
        sut.stowTask(task3);
        sut.nestTask(task1, task2);
        ArrayList<ITask> actual = sut.getTask("00000001").getChildren();
        assertAll(() -> {
            assertEquals("00000002", actual.get(0).getUuid());
            assertEquals("Task 2", actual.get(0).getName());
            assertEquals(Task.Type.IDEA, actual.get(0).getType());
            assertEquals(Task.Priority.LOWEST, actual.get(0).getPriority());
        });
    }
}
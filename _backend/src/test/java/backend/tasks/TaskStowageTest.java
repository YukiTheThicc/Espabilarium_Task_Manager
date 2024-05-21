package backend.tasks;

import backend.api.ITask;
import backend.api.ITaskStowage;
import backend.events.EventSystem;
import backend.exceptions.EspRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskStowageTest {

    ITaskStowage sut;
    ITask task1 = new Task("00000001", "Task 1", TaskType.TASK, TaskPriority.LOWEST);
    ITask task2 = new Task("00000002", "Task 2", TaskType.IDEA, TaskPriority.LOWEST);
    ITask task3 = new Task("00000003", "Task 3", TaskType.PROJECT, TaskPriority.HIGHEST);
    String testDataPath = new File("src/test/resources").getAbsolutePath() + "\\";

    @BeforeEach
    void setUp() {
        sut = new TaskStowage(new EventSystem());
    }

    @Test
    void testStowTask() {
        sut.stowUpdateTask(task1);
        sut.stowUpdateTask(task2);
        sut.stowUpdateTask(task3);
        assertThrows(EspRuntimeException.class, () -> sut.stowUpdateTask(null));
        assertThrows(EspRuntimeException.class, () -> sut.stowUpdateTask(task1));
    }

    @Test
    void testGetTask() {
        sut.stowUpdateTask(task1);
        sut.stowUpdateTask(task2);
        sut.stowUpdateTask(task3);
        ITask actual = sut.getTask("00000002");
        assertAll(() -> {
            assertEquals("00000002", actual.getUuid());
            assertEquals("Task 2", actual.getName());
            assertEquals(TaskType.IDEA, actual.getType());
            assertEquals(TaskPriority.LOWEST, actual.getPriority());
        });
    }

    @Test
    void testNestTask() {
        sut.stowUpdateTask(task1);
        sut.stowUpdateTask(task2);
        sut.stowUpdateTask(task3);
        sut.nestTask(task1, task2);
        ArrayList<ITask> actual = sut.getTask("00000001").getChildren();
        assertAll(() -> {
            assertEquals("00000002", actual.get(0).getUuid());
            assertEquals("Task 2", actual.get(0).getName());
            assertEquals(TaskType.IDEA, actual.get(0).getType());
            assertEquals(TaskPriority.LOWEST, actual.get(0).getPriority());
        });
    }

    @Test
    void testSaveTask() {
        sut.stowUpdateTask(task1);
        sut.stowUpdateTask(task2);
        sut.stowUpdateTask(task3);
        sut.nestTask(task1, task2);
        sut.saveTask(task1, testDataPath);
    }

    @Test
    void testLoadTask() {
        ITask loaded = sut.loadTask(testDataPath, "LOAD_TEST");
        System.out.println(loaded);
        assertAll(() -> {
            assertEquals("00000003", loaded.getUuid());
            assertEquals("Loading test Task", loaded.getName());
            assertEquals(TaskType.PROJECT, loaded.getType());
            assertEquals(TaskPriority.HIGHEST, loaded.getPriority());
        });
    }
}
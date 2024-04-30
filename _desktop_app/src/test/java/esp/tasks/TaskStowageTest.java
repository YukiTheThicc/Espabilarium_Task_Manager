package esp.tasks;

import esp.api.ITask;
import esp.api.ITaskStowage;
import esp.exceptions.EspRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskStowageTest {

    ITaskStowage sut;
    ITask task1 = new Task("00000001", "Task 1", TaskType.TASK, TaskPriority.LOWEST);
    ITask task2 = new Task("00000002", "Task 2", TaskType.IDEA, TaskPriority.LOWEST);
    ITask task3 = new Task("00000003", "Task 3", TaskType.PROJECT, TaskPriority.HIGHEST);

    @BeforeEach
    void setUp() {
        sut = new TaskStowage();
    }

    @Test
    void testStowTask() {
        sut.stowTask(task1);
        sut.stowTask(task2);
        sut.stowTask(task3);
        assertThrows(EspRuntimeException.class, () -> sut.stowTask(null));
        assertThrows(EspRuntimeException.class, () -> sut.stowTask(task1));
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
            assertEquals(TaskType.IDEA, actual.getType());
            assertEquals(TaskPriority.LOWEST, actual.getPriority());
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
            assertEquals(TaskType.IDEA, actual.get(0).getType());
            assertEquals(TaskPriority.LOWEST, actual.get(0).getPriority());
        });
    }
}
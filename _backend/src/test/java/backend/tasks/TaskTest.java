package backend.tasks;

import backend.api.ITask;
import backend.tasks.Task;
import backend.tasks.TaskPriority;
import backend.tasks.TaskState;
import backend.tasks.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    ITask sut;

    @BeforeEach
    void setup() {
        sut = new Task("0000000f", "Test Task", TaskType.TASK, TaskPriority.LOWEST);
    }

    @Test
    void testGetUuid() {
        assertEquals("0000000f", sut.getUuid());
    }

    @Test
    void testGetName() {
        assertEquals("Test Task", sut.getName());
    }

    @Test
    void testGetParent() {
        assertNull(sut.getParent());
    }

    @Test
    void testGetProgress() {
        assertEquals(0, sut.getProgress());
    }

    @Test
    void testGetType() {
        assertEquals(TaskType.TASK, sut.getType());
    }

    @Test
    void testGetState() {
        assertEquals(TaskState.NEW, sut.getState());
    }

    @Test
    void testGetPriority() {
        assertEquals(TaskPriority.LOWEST, sut.getPriority());
    }

    @Test
    void testSetName() {
        String expected = "New name";
        sut.setName("New name");
        assertEquals(expected, sut.getName());
    }

    @Test
    void testSetParent() {
        ITask expected = new Task("f000000", "Parent task", TaskType.TASK, TaskPriority.LOWEST);
        sut.setParent(expected);
        assertEquals(expected.getUuid(), sut.getParent().getUuid());
    }

    @Test
    void testSetProgress() {
        float expected0 = 0f;
        float expected1 = 1f;
        float expected2 = 0f;
        float expected3 = 0.67f;
        assertEquals(expected0, sut.getProgress());
        sut.setProgress(1.64f);
        assertEquals(expected1, sut.getProgress());
        sut.setProgress(-0.475f);
        assertEquals(expected2, sut.getProgress());
        sut.setProgress(0.67f);
        assertEquals(expected3, sut.getProgress());
    }

    @Test
    void testSetType() {
        TaskType expected = TaskType.IDEA;
        sut.setType(TaskType.IDEA);
        assertEquals(expected, sut.getType());
    }

    @Test
    void testSetState() {
        TaskState expected = TaskState.IN_PROGRESS;
        sut.setState(TaskState.IN_PROGRESS);
        assertEquals(expected, sut.getState());
    }

    @Test
    void testSetPriority() {
        TaskPriority expected = TaskPriority.HIGHEST;
        sut.setPriority(TaskPriority.HIGHEST);
        assertEquals(expected, sut.getPriority());
    }

    @Test
    void testAddChild() {
        sut.addChild(new Task("00000001", "Child 1", TaskType.TASK, TaskPriority.LOWEST));
        sut.addChild(new Task("00000002", "Child 2", TaskType.TASK, TaskPriority.LOWEST));
    }

    @Test
    void testGetChildren() {
        ITask child1 = new Task("00000001", "Child 1", TaskType.TASK, TaskPriority.LOWEST);
        ITask child2 = new Task("00000002", "Child 2", TaskType.TASK, TaskPriority.LOWEST);
        sut.addChild(child1);
        sut.addChild(child2);
        ITask[] actual = new ITask[2];
        sut.getChildren().toArray(actual);
        assertArrayEquals(new ITask[]{child1, child2}, actual);
    }

    @Test
    void testRemoveChild() {
        TaskPriority expected = TaskPriority.HIGHEST;
        sut.setPriority(TaskPriority.HIGHEST);
        assertEquals(expected, sut.getPriority());
    }
}
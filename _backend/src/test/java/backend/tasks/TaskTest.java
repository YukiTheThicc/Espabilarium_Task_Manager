package backend.tasks;

import backend.api.ITask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    ITask sut;

    @BeforeEach
    void setup() {
        sut = new Task("0000000f", "Test Task", Task.Type.TASK, Task.Priority.LOWEST);
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
        assertEquals(Task.Type.TASK, sut.getType());
    }

    @Test
    void testGetState() {
        assertEquals(Task.State.NEW, sut.getState());
    }

    @Test
    void testGetPriority() {
        assertEquals(Task.Priority.LOWEST, sut.getPriority());
    }

    @Test
    void testSetName() {
        String expected = "New name";
        sut.setName("New name");
        assertEquals(expected, sut.getName());
    }

    @Test
    void testSetParent() {
        ITask expected = new Task("f000000", "Parent task", Task.Type.TASK, Task.Priority.LOWEST);
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
        Task.Type expected = Task.Type.IDEA;
        sut.setType(Task.Type.IDEA);
        assertEquals(expected, sut.getType());
    }

    @Test
    void testSetState() {
        Task.State expected = Task.State.IN_PROGRESS;
        sut.setState(Task.State.IN_PROGRESS);
        assertEquals(expected, sut.getState());
    }

    @Test
    void testSetPriority() {
        Task.Priority expected = Task.Priority.HIGHEST;
        sut.setPriority(Task.Priority.HIGHEST);
        assertEquals(expected, sut.getPriority());
    }

    @Test
    void testAddChild() {
        sut.addChild(new Task("00000001", "Child 1", Task.Type.TASK, Task.Priority.LOWEST));
        sut.addChild(new Task("00000002", "Child 2", Task.Type.TASK, Task.Priority.LOWEST));
    }

    @Test
    void testGetChildren() {
        ITask child1 = new Task("00000001", "Child 1", Task.Type.TASK, Task.Priority.LOWEST);
        ITask child2 = new Task("00000002", "Child 2", Task.Type.TASK, Task.Priority.LOWEST);
        sut.addChild(child1);
        sut.addChild(child2);
        ITask[] actual = new ITask[2];
        sut.getChildren().toArray(actual);
        assertArrayEquals(new ITask[]{child1, child2}, actual);
    }

    @Test
    void testRemoveChild() {
        Task.Priority expected = Task.Priority.HIGHEST;
        sut.setPriority(Task.Priority.HIGHEST);
        assertEquals(expected, sut.getPriority());
    }
}
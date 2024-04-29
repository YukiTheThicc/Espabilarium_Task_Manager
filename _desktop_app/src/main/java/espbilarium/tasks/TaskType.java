package espbilarium.tasks;

public enum TaskType {

    TASK("task"),
    IDEA("idea"),
    PROJECT("project");

    private final String tag;

    TaskType(String type) {
        this.tag = type;
    }
}

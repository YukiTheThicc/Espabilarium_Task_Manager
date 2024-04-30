package esp.tasks;

public enum TaskState {

    NEW("new", 0),
    ON_HOLD("on_hold", 1),
    IN_PROGRESS("in_progress", 2),
    RESOLVED("resolved", 3),
    FINISHED("finished", 4),
    ARCHIVED("archived", 4);

    private final String tag;

    TaskState(String literal, int code) {
        this.tag = literal;
    }
}

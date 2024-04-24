package espbilarium.utils;

import java.util.HashMap;

/**
 * Defaults
 *
 * @author Santiago Barreiro
 */
public class EspDefaults {

    // METHODS
    public static void defaultLiterals(HashMap<String, String> literals) {
       literals.clear();

        literals.put("file", "File");
        literals.put("window", "Window");
        literals.put("task", "Task");
        literals.put("new_task", "New Task");
        literals.put("project", "Project");
        literals.put("new_project", "New Project");
    }
}

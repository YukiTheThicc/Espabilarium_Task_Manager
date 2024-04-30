package esp.utils;

import esp.ui.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ResourcePool
 *
 * @author Santiago Barreiro
 */
public class Resources {

    // CONSTANTS
    private static final String UNKNOWN_LITERAL = "UNKNOWN";
    private static final String DEFAULT_ICON = "default.png";
    private static final String DEFAULT_ICON_FOLDER = "res/icons";

    // ATTRIBUTES
    private static final HashMap<String, String> literals = new HashMap<>();
    private static final HashMap<String, Object> icons = new HashMap<>();
    private static final HashMap<String, int[]> colors = new HashMap<>();


    // METHODS
    private static void defaultLiterals() {
        literals.clear();

        literals.put("file", "File");
        literals.put("window", "Window");
        literals.put("task", "Task");
        literals.put("new_task", "New Task");
        literals.put("project", "Project");
        literals.put("new_project", "New Project");
    }

    private static void defaultColors() {
        colors.clear();

        // Dialogger colors
        colors.put("DiaLogger.CRITICAL", new int[]{175, 50, 233, 255});
        colors.put("DiaLogger.ERROR", new int[]{200, 50, 50, 255});
        colors.put("DiaLogger.WARN", new int[]{200, 175, 50, 255});
        colors.put("DiaLogger.DEBUG", new int[]{50, 200, 50, 255});
        colors.put("DiaLogger.SAPP_DEBUG", new int[]{54, 75, 108, 255});

        // main theme
        colors.put("default", new int[]{125, 0, 125, 255});
        colors.put("light_grey", new int[]{101, 110, 123, 255});
        colors.put("grey", new int[]{61, 66, 72, 255});
        colors.put("main_bg", new int[]{41, 46, 52, 255});
        colors.put("dark_bg", new int[]{25, 31, 37, 255});
        colors.put("highlight", new int[]{93, 139, 191, 255});
        colors.put("accent", new int[]{64, 79, 100, 255});
        colors.put("inactive", new int[]{30, 38, 57, 255});

        // Others
        colors.put("font", new int[]{218, 224, 232, 255});
    }

    private static void loadAvailableIcons() {
        ArrayList<File> iconsFiles = Utils.getFilesInDir("res/icons", "png");
        for (File icon : iconsFiles) {
            if (icon != null && icon.isFile()) {
                Image iconTex = new Image();
                iconTex.load(icon.getAbsolutePath());
                if (iconTex.getId() != 0 && !"".equals(iconTex.getPath())) {
                    icons.put(icon.getName(), iconTex);
                }
            }
        }
    }

    private static boolean loadLiteralsFromFile(String file) {
        boolean validFile = false;
        // Glue code for literal
        if (!validFile) {
            defaultLiterals();
        }
        return true;
    }

    private static boolean loadColorTheme(String file) {
        boolean validFile = false;
        // Glue code for literal
        if (!validFile) {
            defaultLiterals();
        }
        return true;
    }

    public static void init(String literalsFile) {
        defaultColors();
        defaultLiterals();
        loadAvailableIcons();
    }

    public static String literal(String key) {
        if (literals.get(key) != null) return literals.get(key);
        return key + ": " + UNKNOWN_LITERAL;
    }

    public static Object icon(String icon) {
        if (icons.get(icon) != null) {
            return icons.get(icon);
        }
        return icons.get("default.png");
    }

    public static int[] color(String key) {
        if (colors.get(key) != null) return colors.get(key);
        return new int[] {175, 50, 233, 255};
    }
}

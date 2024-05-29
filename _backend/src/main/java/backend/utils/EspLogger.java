package backend.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * !!! ATTENTION !!! THIS IS A PLACEHOLDER CLASS
 *
 * @author Santiago Barreiro
 */
public class EspLogger {

    public enum Level {
        CRITICAL,
        ERROR,
        WARN,
        DEBUG,
        INFO,
    }


    // CONSTANTS
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("'['hh:mm:ss.SSS']'");

    // ATTRIBUTES

    // CONSTRUCTORS

    // GETTERS & SETTERS

    // METHODS
    public static void log(Class<?> caller, String message, Level level) {
        System.out.println(dateFormat.format(Calendar.getInstance().getTime()) +
                (caller != null ? "[" + caller + "]:" : "") +
                (level != null ? "[" + level + "]" : "\t") + " " +
                message
        );
    }

    public static void log(Class<?> caller, String message) {
        log(caller, message, Level.DEBUG);
    }

    public static void log(String message, Level level) {
        log(null, message, level);
    }

    public static void log(String message) {
        log(null, message, Level.DEBUG);
    }
}

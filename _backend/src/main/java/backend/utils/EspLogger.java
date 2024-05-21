package backend.utils;

/**
 * !!! ATTENTION !!! THIS IS A PLACEHOLDER CLASS
 *
 * @author Santiago Barreiro
 */
public class EspLogger {

    // CONSTANTS


    // ATTRIBUTES


    // CONSTRUCTORS


    // GETTERS & SETTERS


    // METHODS
    public static void log(Class<?> caller, String message, EspLoggerLevel level) {
        System.out.println("[" + caller + "]:" + message);
    }
    public static void log(Class<?> caller, String message) {
        System.out.println("[" + caller + "]:" + message);
    }

    public static void log(String message, EspLoggerLevel level) {
        System.out.println(message);
    }

    public static void log(String message) {
        System.out.println(message);
    }
}

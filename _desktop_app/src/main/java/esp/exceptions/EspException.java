package esp.exceptions;

/**
 * EspException
 *
 * @author Santiago Barreiro
 */
public class EspException extends Exception {

    public EspException() {
        super("GENERIC EXCEPTION");
    }

    public EspException(String message) {
        super(message);
    }

    public EspException(Class<?> origin, String message) {
        super("[" + origin.getSimpleName() + "] -> " + message);
    }
}

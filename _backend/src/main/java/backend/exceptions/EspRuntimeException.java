package backend.exceptions;

/**
 * EspabilariumException
 *
 * @author Santiago Barreiro
 */
public class EspRuntimeException extends RuntimeException {

    public EspRuntimeException() {
        super("GENERIC RUNTIME EXCEPTION");
    }

    public EspRuntimeException(String message) {
        super(message);
    }

    public EspRuntimeException(Class<?> origin, String message) {
        super("[" + origin.getSimpleName() + "] -> " + message);
    }
}

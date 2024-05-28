package backend.api;

/**
 * INotificator
 *
 * @author Santiago Barreiro
 */
public interface INotifier {

    void notifyUser(String message);

    void warnUser(String message);
}

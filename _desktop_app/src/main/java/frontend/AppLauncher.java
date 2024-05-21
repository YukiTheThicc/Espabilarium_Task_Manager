package frontend;

/**
 * launcher
 *
 * @author Santiago Barreiro
 */
public class AppLauncher {
    // METHODS
    public static void main(String[] args) {
        DesktopFront app = new DesktopFront();
        boolean debug = false;
        for (String arg : args) {
            String[] argSplit = arg.split("=");
            if (argSplit[0].equals("-debug")) {
                if ("1".equals(argSplit[1])) debug = true;
            }
        }
        app.launch(debug);
    }
}

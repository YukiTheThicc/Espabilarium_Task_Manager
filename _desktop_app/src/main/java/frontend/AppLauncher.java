package frontend;

/**
 * launcher
 *
 * @author Santiago Barreiro
 */
public class AppLauncher {
    // METHODS
    public static void main(String[] args) {
        EspabilariumDesktop app = new EspabilariumDesktop();
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

package esp;

import java.util.Arrays;

/**
 * launcher
 *
 * @author Santiago Barreiro
 */
public class AppLauncher {
    // METHODS
    public static void main(String[] args) {
        Espabilarium app = new Espabilarium();
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

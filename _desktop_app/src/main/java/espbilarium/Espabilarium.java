package espbilarium;

import espbilarium.ui.imgui.ImGuiLayer;
import espbilarium.ui.Window;
import espbilarium.utils.EspDefaults;
import espbilarium.utils.EspStyles;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

/**
 * espbilarium.Espabilarium
 *
 * @author Santiago Barreiro
 */
public class Espabilarium {

    // CONSTANTS
    private static final String UNKNOWN_LITERAL = "LIT_";

    // ATTRIBUTES
    private static HashMap<String, String> literals = new HashMap<>();
    private final Window window;
    private ImGuiLayer imgui;

    // CONSTRUCTORS
    public Espabilarium() {
        this.window = Window.get();
        this.imgui = null;
    }

    // METHODS
    private static void fetchLiterals(String lang) {
        boolean validFile = false;
        // Glue code for literal
        if (!validFile) {
            EspDefaults.defaultLiterals(literals);
        }
    }

    public static String getLiteral(String literal) {
        if (literals.get(literal) != null) return literals.get(literal);
        return literal + ": " + UNKNOWN_LITERAL;
    }

    public void launch() {

        // Initialize program
        window.init("Espabilarium", "icon.png");
        imgui = new ImGuiLayer(window.getGlfwWindow());
        imgui.init();
        EspDefaults.defaultLiterals(literals);
        EspStyles.setEspStyles();

        // Run program
        run();

        // Close program
        imgui.destroyImGui();
        window.close();
    }

    public void run() {
        boolean running = true;
        while (running) {
            window.pollEvents();

            imgui.update();

            window.endFrame();
            running = !shouldClose();
        }
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(this.window.getGlfwWindow());
    }
}

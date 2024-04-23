import ui.imgui.ImGuiLayer;
import ui.Window;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

/**
 * Espabilarium
 *
 * @author Santiago Barreiro
 */
public class Espabilarium {

    // CONSTANTS


    // ATTRIBUTES
    private final Window window;
    private ImGuiLayer imgui;

    // CONSTRUCTORS
    public Espabilarium() {
        this.window = Window.get();
        this.imgui = null;
    }

    // GETTERS & SETTERS


    // METHODS
    public void launch() {

        // Initialize program
        window.init("Sapphire", "sapphire/icon.png");
        imgui = new ImGuiLayer(window.getGlfwWindow());
        imgui.init();

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

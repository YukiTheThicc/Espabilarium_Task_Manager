package espbilarium;

import espbilarium.ui.ImGuiLayer;
import espbilarium.ui.Window;
import espbilarium.utils.Resources;

import java.util.Calendar;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

/**
 * espbilarium.Espabilarium
 *
 * @author Santiago Barreiro
 */
public class Espabilarium {

    // ATTRIBUTES
    private final Window window;
    private ImGuiLayer imgui;

    // CONSTRUCTORS
    public Espabilarium() {
        this.window = Window.get();
        this.imgui = null;
    }

    // METHODS
    public void launch() {
        // Initialize program

        // Init window and imgui layer
        window.init("Espabilarium", "icon.png");
        imgui = new ImGuiLayer(window.getGlfwWindow());
        imgui.init();

        // Initialize the resource pool and styles
        Resources.init("file");

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

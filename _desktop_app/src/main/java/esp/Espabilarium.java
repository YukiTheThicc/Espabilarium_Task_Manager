package esp;

import esp.api.IEvent;
import esp.api.IObserver;
import esp.events.Event;
import esp.events.EventSystem;
import esp.ui.ImGuiLayer;
import esp.ui.Window;
import esp.utils.Resources;

import java.util.ArrayList;
import java.util.HashMap;

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
    private final EventSystem eventSystem;

    // CONSTRUCTORS
    public Espabilarium() {
        this.window = Window.get();
        this.imgui = null;
        this.eventSystem = new EventSystem();
    }

    // METHODS
    public void launch() {

        // Init window and imgui layer
        window.init("Espabilarium", "icon.png");
        imgui = new ImGuiLayer(window.getGlfwWindow());
        imgui.init();

        // Initialize the resource pool and styles
        Resources.init("file");

        // Add observers to

        // Run program
        run();

        // Close program
        imgui.destroyImGui();
        window.close();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(this.window.getGlfwWindow());
    }

    private void run() {
        boolean running = true;
        while (running) {
            window.pollEvents();

            eventSystem.handleEvents();
            imgui.update();

            window.endFrame();
            running = !shouldClose();
        }
    }
}

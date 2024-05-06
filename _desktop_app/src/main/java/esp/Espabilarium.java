package esp;

import esp.tasks.TaskState;
import esp.tasks.TaskStowage;
import esp.ui.ImGuiLayer;
import esp.ui.Window;
import esp.utils.Resources;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
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
    private TaskStowage stowage;

    // CONSTRUCTORS
    public Espabilarium() {
        this.window = Window.get();
        this.imgui = null;
        this.stowage = new TaskStowage();
    }

    // METHODS
    public void launch() {
        // Initialize program

        // Init window and imgui layer
        window.init("Espabilarium", "app.png");
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
        float bt = (float) glfwGetTime();
        float et;
        float dt = 0f;
        while (running) {
            window.pollEvents();
            if (dt >= 0) {
                imgui.update(dt);
            }
            window.endFrame();

            et = (float) glfwGetTime();
            dt = et - bt;
            bt = et;
            running = !shouldClose();
        }
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(this.window.getGlfwWindow());
    }
}

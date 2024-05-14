package esp;

import esp.events.UIEventObserver;
import esp.tasks.TaskQueryMaker;
import esp.tasks.TaskStowage;
import esp.events.EventSystem;
import esp.ui.ImGuiLayer;
import esp.ui.UserInterface;
import esp.ui.Window;
import esp.utils.Resources;

import static esp.events.UIEvent.Type.*;
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
    private final TaskStowage stowage;
    private final EventSystem eventSystem;
    private final TaskQueryMaker queryMaker;

    // CONSTRUCTORS
    public Espabilarium() {
        this.window = Window.get();
        this.imgui = null;
        this.eventSystem = new EventSystem();
        this.stowage = new TaskStowage(this.eventSystem);
        this.queryMaker = new TaskQueryMaker();
    }

    // METHODS
    public void launch(boolean debug) {

        // Init window and imgui layer
        window.init("Espabilarium", "app.png");
        UserInterface ui = new UserInterface(eventSystem, queryMaker, debug);
        imgui = new ImGuiLayer(window.getGlfwWindow(), ui);
        imgui.init();

        // Connect the query maker to the stowage instance
        queryMaker.connectStowage(stowage);

        // Create and connect the UIEventObserver to the event system
        eventSystem.attachObserver(new Enum[]{SAVE_TASK, CREATE_TASK, USER_EVENT}, new UIEventObserver(stowage));

        // Initialize the resource pool and styles
        Resources.init("file");

        run();
        imgui.destroyImGui();
        window.close();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(this.window.getGlfwWindow());
    }

    private void run() {
        boolean running = true;
        float bt = (float) glfwGetTime();
        float et;
        float dt = 0f;
        while (running) {

            window.pollEvents();
            eventSystem.dispatchEvents();

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
}

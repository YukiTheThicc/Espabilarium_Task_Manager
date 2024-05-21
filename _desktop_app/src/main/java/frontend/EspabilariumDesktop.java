package frontend;

import frontend.events.EventObserver;
import backend.tasks.TaskQueryMaker;
import backend.tasks.TaskStowage;
import backend.events.EventSystem;
import frontend.ui.ImGuiLayer;
import frontend.ui.UserInterface;
import frontend.ui.Window;
import frontend.utils.Resources;

import static frontend.events.Event.Type.*;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

/**
 * espbilarium.Espabilarium
 *
 * @author Santiago Barreiro
 */
public class EspabilariumDesktop {

    // ATTRIBUTES
    private final Window window;
    private ImGuiLayer imgui;
    private final TaskStowage stowage;
    private final EventSystem eventSystem;
    private final TaskQueryMaker queryMaker;

    // CONSTRUCTORS
    public EspabilariumDesktop() {
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
        eventSystem.attachObserver(new Enum[]{STOW_TASK, USER_EVENT}, new EventObserver(stowage));

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

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
    private boolean running = false;

    // CONSTRUCTORS
    public Espabilarium() {
        this.window = Window.get();
    }

    // GETTERS & SETTERS


    // METHODS
    public void launch() {
        window.init("Sapphire", "sapphire/icon.png");
        run();
    }

    public void update() {

    }

    public void run() {
        running = true;
        while (running) {
            window.pollEvents();

            window.endFrame();
            running = !shouldClose();
        }
        window.close();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(this.window.getGlfwWindow());
    }
}

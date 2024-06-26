package esp.ui;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import esp.utils.Utils;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Window
 *
 * @author Santiago Barreiro
 */
public class Window {

    // ATTRIBUTES
    private static int width, height;
    private IntBuffer posX, posY;
    private String title;
    private long glfwWindow;
    private static Window window = null;
    private long audioContext;
    private long audioDevice;

    // CONSTRUCTORS
    private Window() {
        this.width = 1200;
        this.height = 800;
        stackPush();
        this.posX = stackCallocInt(1);
        stackPush();
        this.posY = stackCallocInt(1);
        this.title = "DiamondEngine v0.0.0.1";
        stackPop();
        stackPop();
    }

    // GETTERS & SETTERS
    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getGlfwWindow() {
        return glfwWindow;
    }

    public static void setWidth(int width) {
        Window.width = width;
    }

    public static void setHeight(int height) {
        Window.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Vector2f getPosition() {
        Window window = get();
        Vector2f pos = new Vector2f();
        org.lwjgl.glfw.GLFW.glfwGetWindowPos(window.glfwWindow, window.posX, window.posY);
        pos.x = window.posX.get(0);
        pos.y = window.posY.get(0);
        return pos;
    }

    public static float getAspectRatio() {
        return (float) width / height;
    }

    // METHODS
    public void resizeCallback() {
        System.out.println("Resizing");
    }

    public void init(String title, String iconPath) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        // Set default window state
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // Create the window itself
        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseControls::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseControls::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseControls::mouseScrollCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newX, newY) -> {
            Window.setWidth(newX);
            Window.setHeight(newY);
        });

        // Setup audio context
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);
        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        assert alCapabilities.OpenAL10 : "Audio library not supported";

        // Setup context and show window
        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        GL.createCapabilities();
        glDisable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glfwShowWindow(glfwWindow);

        GLFWImage.Buffer image = Utils.loadGLFWImage(iconPath);
        if (image != null) {
            glfwSetWindowIcon(glfwWindow, image);
            image.close();
        }
    }

    public static Window get() {
        if (window == null) {
            window = new Window();
        }
        return Window.window;
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void startFrame() {

    }

    public void endFrame() {
        glfwSwapBuffers(glfwWindow);
    }

    public void close() {
        // Free allocated memory
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        posX = null;
        posY = null;

        // Termination of GLFW
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}

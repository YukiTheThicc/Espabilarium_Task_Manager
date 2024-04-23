package ui.imgui;

import imgui.*;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.internal.ImGuiDockNode;
import imgui.type.ImBoolean;
import org.joml.Vector2f;
import ui.MouseControls;
import ui.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

/**
 * ImGui
 *
 * @author Santiago Barreiro
 */
public class ImGuiLayer {

    // ATTRIBUTES
    private final long glfwWindow;
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final UserInterface ui;
    private int dockId;

    // CONSTRUCTORS
    public ImGuiLayer(long windowPtr) {
        this.glfwWindow = windowPtr;
        this.ui = new UserInterface(this);
        this.dockId = -1;
    }

    // GETTERS & SETTERS
    public int getDockId() {
        return dockId;
    }

    // METHODS
    public void init() {

        ImGui.createContext();
        ImPlot.createContext();
        final ImGuiIO io = ImGui.getIO();

        io.setIniFilename("imgui.ini");
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        io.setBackendPlatformName("imgui_java_impl_glfw");

        initCallbacks(io);

        // Set up clipboard functionality
        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(final String s) {
                glfwSetClipboardString(glfwWindow, s);
            }
        });

        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                final String clipboardString = glfwGetClipboardString(glfwWindow);
                if (clipboardString != null) {
                    return clipboardString;
                } else {
                    return "";
                }
            }
        });

        // !!! REVISE !!! Should be false once all callbacks are set up
        imGuiGlfw.init(glfwWindow, true);
        imGuiGl3.init("#version 330 core");
    }

    private void initCallbacks(ImGuiIO io) {
        // GLFW callbacks to handle user input
        glfwSetKeyCallback(glfwWindow, (w, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                io.setKeysDown(key, true);
            } else if (action == GLFW_RELEASE) {
                io.setKeysDown(key, false);
            }

            io.addConfigFlags(ImGuiConfigFlags.NavNoCaptureKeyboard);

            io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
            io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
            io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
            io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));

        });

        glfwSetMouseButtonCallback(glfwWindow, (w, button, action, mods) -> {
            final boolean[] mouseDown = new boolean[5];

            mouseDown[0] = button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE;
            mouseDown[1] = button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE;
            mouseDown[2] = button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE;
            mouseDown[3] = button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE;
            mouseDown[4] = button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE;

            io.setMouseDown(mouseDown);

            if (!io.getWantCaptureMouse() && mouseDown[1]) {
                imgui.internal.ImGui.setWindowFocus(null);
            }

            if (!io.getWantCaptureMouse()) {
                MouseControls.mouseButtonCallback(w, button, action, mods);
            }
        });

        glfwSetScrollCallback(glfwWindow, (w, xOffset, yOffset) -> {
            io.setMouseWheelH(io.getMouseWheelH() + (float) xOffset);
            io.setMouseWheel(io.getMouseWheel() + (float) yOffset);


            if (!io.getWantCaptureMouse()) {
                MouseControls.mouseScrollCallback(w, xOffset, yOffset);
            }
        });
    }

    private void endFrame() {

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT);
        // After Dear ImGui prepared a draw data, we use it in the LWJGL3 renderer.
        // At that moment ImGui will be rendered to the current OpenGL context.
        imgui.internal.ImGui.render();
        imGuiGl3.renderDrawData(imgui.internal.ImGui.getDrawData());

        long backupWindowPtr = glfwGetCurrentContext();
        imgui.internal.ImGui.updatePlatformWindows();
        imgui.internal.ImGui.renderPlatformWindowsDefault();
        glfwMakeContextCurrent(backupWindowPtr);
    }

    private void setupDockSpace() {

        int windowFlags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking | ImGuiWindowFlags.NoTitleBar |
                ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

        ImGuiViewport mainViewport = imgui.internal.ImGui.getMainViewport();
        imgui.internal.ImGui.setNextWindowPos(mainViewport.getWorkPosX(), mainViewport.getWorkPosY());
        imgui.internal.ImGui.setNextWindowSize(mainViewport.getWorkSizeX() * 2, mainViewport.getWorkSizeY());
        imgui.internal.ImGui.setNextWindowViewport(mainViewport.getID());
        Vector2f windowPos = Window.getPosition();
        imgui.internal.ImGui.setNextWindowPos(windowPos.x, windowPos.y);
        imgui.internal.ImGui.setNextWindowSize(Window.getWidth(), Window.getHeight());

        imgui.internal.ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 8);
        imgui.internal.ImGui.begin("Dockspace Outer", new ImBoolean(true), windowFlags);
        imgui.internal.ImGui.popStyleVar();

        // Dockspace
        dockId = imgui.internal.ImGui.dockSpace(imgui.internal.ImGui.getID("Dockspace"));
        imgui.ImGui.setNextWindowDockID(dockId);

        imgui.internal.ImGui.end();
    }

    private void startFrame() {
        imGuiGlfw.newFrame();
        imgui.internal.ImGui.newFrame();
    }

    public void update() {
        startFrame();
        setupDockSpace();
        ImGuiDockNode node = imgui.internal.ImGui.dockBuilderGetCentralNode(dockId);
        imgui.internal.ImGui.setNextWindowDockID(node.getID());

        ui.render();

        endFrame();
    }

    public void destroyImGui() {
        imGuiGl3.dispose();
        imgui.internal.ImGui.destroyContext();
    }
}

package esp.ui;

import esp.utils.EspLogger;
import esp.utils.EspStyles;
import esp.utils.Utils;
import imgui.*;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import java.io.File;
import java.util.ArrayList;

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
    private ImFont normalFont;
    private ImFont bigFont;
    private ImFont smallFont;
    private boolean firstFrame = true;

    // CONSTRUCTORS
    public ImGuiLayer(long windowPtr, UserInterface ui) {
        this.glfwWindow = windowPtr;
        this.ui = ui;
    }

    // GETTERS
    public ImFont getNormalFont() {
        return normalFont;
    }

    public ImFont getBigFont() {
        return bigFont;
    }

    public ImFont getSmallFont() {
        return smallFont;
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
        addAvailableFonts(io);
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
                ImGui.setWindowFocus(null);
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

    private void addAvailableFonts(imgui.ImGuiIO io) {

        ArrayList<File> fontFiles = Utils.getFilesInDir("res/fonts", "ttf");
        fontFiles.addAll(Utils.getFilesInDir("res/fonts", "otf"));
        if (!fontFiles.isEmpty()) {
            final ImFontAtlas fontAtlas = io.getFonts();
            final ImFontConfig fontConfig = new ImFontConfig();
            fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());
            fontConfig.setPixelSnapH(true);
            for (File file : fontFiles) {
                this.normalFont = fontAtlas.addFontFromFileTTF(file.getAbsolutePath(), 16f, fontConfig);
                this.bigFont = fontAtlas.addFontFromFileTTF(file.getAbsolutePath(), 24f, fontConfig);
                this.smallFont = fontAtlas.addFontFromFileTTF(file.getAbsolutePath(), 12f, fontConfig);
                imgui.internal.ImGui.getIO().setFontDefault(normalFont);
            }
        } else {
            EspLogger.log("Failed to load any font files from fonts dir");
        }
    }

    private void endFrame() {

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT);
        // After Dear ImGui prepared a draw data, we use it in the LWJGL3 renderer.
        // At that moment ImGui will be rendered to the current OpenGL context.
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        long backupWindowPtr = glfwGetCurrentContext();
        ImGui.updatePlatformWindows();
        ImGui.renderPlatformWindowsDefault();
        glfwMakeContextCurrent(backupWindowPtr);
    }

    private void startFrame() {
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    /**
     * Renders the ui. On the first frame certain initialization operations must be made (necessary because some panels
     * and other setting may need access to ImGui, so the frame has to be started)
     */
    public void update(float dt) {
        startFrame();
        // Initialize whatever needs to be initialized when ImGui is accessible
        if (firstFrame) {
            EspStyles.setEspStyles(ImGui.getFontSize());
            ui.init(this);
            firstFrame = false;
        }
        ui.render(dt);
        endFrame();
    }

    public void destroyImGui() {
        imGuiGl3.dispose();
        ImGui.destroyContext();
    }
}

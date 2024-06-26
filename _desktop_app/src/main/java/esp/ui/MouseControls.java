package esp.ui;

import org.joml.Vector2f;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseControls {

    private static double scrollX, scrollY;
    private static double xPos, yPos;
    private static final boolean[] mouseButtonPressed = new boolean[9];
    private static boolean isDragging;
    private static int mouseButtonDown = 0;
    private static final Vector2f gameViewportPos = new Vector2f();
    private static final Vector2f gameViewportSize = new Vector2f();

    // CALLBACKS
    public static void mousePosCallback(long window, double xpos, double ypos) {
        if (mouseButtonDown > 0) {
            isDragging = true;
        }
        xPos = xpos;
        yPos = ypos;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            mouseButtonDown++;
            if (button < mouseButtonPressed.length) {
                mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            mouseButtonDown--;
            if (button < mouseButtonPressed.length) {
                mouseButtonPressed[button] = false;
                isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        scrollX = xOffset;
        scrollY = yOffset;
    }

    // GETTERS & SETTERS
    public static float getX() {
        return (float) xPos;
    }

    public static float getY() {
        return (float) yPos;
    }

    public static float getScrollX() {
        return (float) scrollX;
    }

    public static float getScrollY() {
        return (float) scrollY;
    }

    public static Vector2f getGameViewportPos() {
        return gameViewportPos;
    }

    public static void setGameViewportPos(float x, float y) {
        MouseControls.gameViewportPos.x = x;
        MouseControls.gameViewportPos.y = y;
    }

    public static Vector2f getGameViewportSize() {
        return gameViewportSize;
    }

    public static void setGameViewportSize(float x, float y) {
        MouseControls.gameViewportSize.x = x;
        MouseControls.gameViewportSize.y = y;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < mouseButtonPressed.length) {
            return mouseButtonPressed[button];
        } else {
            return false;
        }
    }

    // METHODS
    public static void endFrame() {
        scrollY = 0.0;
        scrollX = 0.0;
    }

    public static void clear() {
        scrollX = 0.0;
        scrollY = 0.0;
        xPos = 0.0;
        yPos = 0.0;
        mouseButtonDown = 0;
        isDragging = false;
        Arrays.fill(mouseButtonPressed, false);
    }

    public static float getScreenX() {
        return getScreen().x;
    }

    public static float getScreenY() {
        return getScreen().y;
    }

    public static Vector2f getScreen() {
        float currentX = getX() - gameViewportPos.x;
        currentX = (currentX / gameViewportSize.x) * Window.getWidth();
        float currentY = (getY() - gameViewportPos.y);
        currentY = (1.0f - (currentY / gameViewportSize.y)) * Window.getHeight();
        return new Vector2f(currentX, currentY);
    }
}

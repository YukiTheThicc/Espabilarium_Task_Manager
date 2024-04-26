package espbilarium.ui.panels;

import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import espbilarium.ui.ImGuiLayer;

/**
 * Panel
 *
 * @author Santiago Barreiro
 */
public abstract class Panel {

    // CONSTANTS
    private static final float DEFAULT_SIZE_X = 400f;
    private static final float DEFAULT_SIZE_Y = 400f;
    private static final int DEFAULT_FLAGS = ImGuiWindowFlags.NoFocusOnAppearing | ImGuiWindowFlags.NoCollapse;

    // ATTRIBUTES
    private float sizeX;
    private float sizeY;
    private final String id;
    private final String title;
    private final ImBoolean isActive;
    private boolean shouldClose;
    private int flags;

    // CONSTRUCTORS
    public Panel(String id, String title) {
        this.id = id;
        this.title = title;
        this.isActive = new ImBoolean(false);
        this.shouldClose = false;
        this.flags = DEFAULT_FLAGS;
        this.sizeX = DEFAULT_SIZE_X;
        this.sizeY = DEFAULT_SIZE_Y;
    }

    // GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ImBoolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive.set(active);
    }

    public boolean shouldClose() {
        return shouldClose;
    }

    public void close(boolean close) {
        this.shouldClose = close;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = DEFAULT_FLAGS | flags;
    }

    public void setAllFlags(int flags) {
        this.flags = flags;
    }

    public float getSizeX() {
        return sizeX;
    }

    public void setSizeX(float sizeX) {
        this.sizeX = sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public void setSizeY(float sizeY) {
        this.sizeY = sizeY;
    }

    // METHODS
    public abstract void renderPanel(ImGuiLayer layer);
}

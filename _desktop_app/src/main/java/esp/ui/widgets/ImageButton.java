package esp.ui.widgets;

import esp.ui.Image;
import esp.utils.Resources;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;

public class ImageButton {

    /**
     * This class is here to retain the state of a button that doesn't change at runtime to reduce load
     */
    // ATTRIBUTES
    private final Image image;
    private final String label;
    private final float imageSizeX;
    private final float imageSizeY;
    private float buttonOriginX;
    private float buttonOriginY;
    private final Enum<?> code;                       // Enum value for whatever this selectable is supposed to select

    // CONSTRUCTORS
    public ImageButton(Image image, String label, float sizeX, float sizeY, Enum<?> code) {
        this.image = image;
        this.label = label;
        this.buttonOriginX = ImGui.getCursorPosX();
        this.buttonOriginY = ImGui.getCursorPosX();
        this.imageSizeX = sizeX;
        this.imageSizeY = sizeY;
        this.code = code;
    }

    public ImageButton(Image image, String label, float sizeX, float sizeY) {
        this.image = image;
        this.label = label;
        this.buttonOriginX = ImGui.getCursorPosX();
        this.buttonOriginY = ImGui.getCursorPosX();
        this.imageSizeX = sizeX;
        this.imageSizeY = sizeY;
        this.code = null;
    }

    // GETTERS
    public Enum<?> getCode() {
        return code;
    }

    // METHODS
    public boolean render(boolean isActive) {
        return render(isActive, false);
    }

    public boolean render(boolean isActive, boolean collapsed) {
        boolean result = false;
        ImGui.pushID(label);
        ImGui.beginGroup();

        /* The original position of the cursor is stored so other items can be placed taking the original position of the
         * button
        */
        buttonOriginX = ImGui.getCursorPosX();
        buttonOriginY = ImGui.getCursorPosY();
        ImVec2 padding = ImGui.getStyle().getFramePadding();

        // If the button is active set the color as such
        if (isActive) {
            int[] color = Resources.color("accent");
            ImGui.pushStyleColor(ImGuiCol.Button, color[0], color[1], color[2], color[3]);
        }

        if (ImGui.button("##", ImGui.getContentRegionAvailX(),
                imageSizeY + padding.y * 2)) result = true;
        if (image.getId() != -1) {
            ImGui.sameLine();
            // Apply margins to the inner button
            ImGui.setCursorPos(buttonOriginX + padding.x, buttonOriginY + padding.y);
            ImGui.image(image.getId(), imageSizeX, imageSizeY, 0, 1, 1, 0);
        }

        // Move cursor to be centered vertically for the button and apply margin taking the image into account
        ImGui.setCursorPos(this.imageSizeX + padding.x * 8,
                buttonOriginY + padding.y + (this.imageSizeY - ImGui.getFontSize()) / 2);

        if (!collapsed) ImGui.textWrapped(label);

        // If the button is active set the color as such
        if (isActive) ImGui.popStyleColor();

        ImGui.endGroup();
        ImGui.popID();
        return result;
    }
}

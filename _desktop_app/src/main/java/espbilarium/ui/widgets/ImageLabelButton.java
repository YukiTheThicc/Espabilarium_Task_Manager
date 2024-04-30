package espbilarium.ui.widgets;

import espbilarium.ui.Image;
import imgui.ImGui;

import javax.swing.text.StyledEditorKit;

public class ImageLabelButton {

    public enum Style {
        ADAPTIVE,
        FIXED
    }

    /**
     * This class is here to retain the state of a button that doesn't change at runtime to reduce load
     */
    // ATTRIBUTES
    private final Image image;
    private final String label;
    private final float buttonSizeX;
    private final float imageSizeX;
    private final float imageSizeY;
    private float buttonOriginX;

    // CONSTRUCTORS
    public ImageLabelButton(Image image, String label, float sizeX, float sizeY) {
        this.image = image;
        this.label = label;
        this.buttonSizeX = ImGui.getContentRegionAvailX();
        this.buttonOriginX = ImGui.getCursorPosX();
        this.imageSizeX = sizeX;
        this.imageSizeY = sizeY;
    }

    public ImageLabelButton(Image image, String label, float sizeX, float sizeY, Style style) {
        this.image = image;
        this.label = label;
        if (style == Style.FIXED) this.buttonSizeX = sizeX + ImGui.calcTextSize(label).x + ImGui.getStyle().getFramePaddingX() * 4;
        else this.buttonSizeX = ImGui.getContentRegionAvailX();
        this.buttonOriginX = ImGui.getCursorPosX();
        this.imageSizeX = sizeX;
        this.imageSizeY = sizeY;
    }


    // METHODS
    public boolean render() {
        boolean result = false;
        ImGui.pushID(label);
        ImGui.beginGroup();
        buttonOriginX = ImGui.getCursorPosX();
        if (ImGui.button(label, buttonSizeX, imageSizeY)) result = true;
        if (image.getId() != -1) {
            ImGui.sameLine();
            ImGui.setCursorPosX(buttonOriginX);
            ImGui.image(image.getId(), imageSizeX, imageSizeY, 0, 1, 1, 0);
        }
        ImGui.endGroup();
        ImGui.popID();
        return result;
    }
}

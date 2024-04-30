package espbilarium.utils;

import espbilarium.ui.AlignX;
import espbilarium.ui.AlignY;
import espbilarium.utils.EspLogger;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImInt;
import imgui.type.ImString;

public class ImGuiUtils {

    // FLAGS
    private static final int NODE_FLAGS = ImGuiTreeNodeFlags.DefaultOpen | ImGuiTreeNodeFlags.Selected |
            ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.OpenOnDoubleClick;

    // ----> SECTION START: INPUTS AND VARIABLE CONTROLS

    public static boolean inputInt(String label, ImInt value) {
        ImGui.pushID(label);

        ImGui.columns(2);
        ImGui.setColumnWidth(0, (ImGui.getWindowWidth()) / 3);
        ImGui.textWrapped(label);
        ImGui.nextColumn();

        ImGui.pushItemWidth(ImGui.getContentRegionAvailX() + ImGui.getStyle().getFramePaddingX() * 2);
        if (ImGui.inputInt("##" + label, value)) {
            ImGui.columns(1);
            ImGui.popID();
            return true;
        }
        ImGui.popItemWidth();

        ImGui.columns(1);
        ImGui.popID();

        return false;
    }

    public static boolean inputText(String label, ImString text) {
        ImGui.pushID(label);

        ImGui.columns(2);
        ImGui.setColumnWidth(0, (ImGui.getWindowWidth()) / 3);
        ImGui.textWrapped(label);
        ImGui.nextColumn();

        ImGui.pushItemWidth(ImGui.getContentRegionAvailX() + ImGui.getStyle().getFramePaddingX() * 2);
        if (ImGui.inputText("##" + label, text)) {
            ImGui.columns(1);
            ImGui.popID();
            return true;
        }
        ImGui.popItemWidth();

        ImGui.columns(1);
        ImGui.popID();

        return false;
    }

    public static boolean checkboxLabel(String label, boolean checked) {
        ImGui.pushID(label);

        ImGui.columns(2);
        ImGui.setColumnWidth(0, (ImGui.getWindowWidth()) / 3);
        ImGui.textWrapped(label);
        ImGui.nextColumn();
        boolean changed = ImGui.checkbox("##" + label, checked);
        ImGui.columns(1);
        ImGui.popID();
        return changed;
    }

    public static String combo(String title, String selected, String[] options) {

        String result = null;
        ImGui.pushID(title);
        ImGui.columns(2);
        ImGui.setColumnWidth(0, (ImGui.getWindowWidth()) / 3);
        ImGui.textWrapped(title);
        ImGui.nextColumn();
        if (ImGui.beginCombo("##" + title, selected)) {
            for (String option : options) {
                ImGui.pushID(option);
                if (ImGui.selectable(option)) {
                    EspLogger.log("Selected '" + option + "'");
                    result = option;
                }
                ImGui.popID();
            }
            ImGui.endCombo();
        }
        ImGui.columns(1);
        ImGui.popID();
        return result;
    }

    public static boolean combo(String title, ImInt index, String[] options, float labelColWidth) {

        ImGui.pushID(title);
        ImGui.columns(2);
        ImGui.setColumnWidth(0, labelColWidth);
        ImGui.textWrapped(title);
        ImGui.nextColumn();

        if (ImGui.combo("##" + title, index, options)) {
            ImGui.popID();
            return true;
        }
        ImGui.columns(1);
        ImGui.popID();
        return false;
    }
    // ----> SECTION END: INPUTS AND VARIABLE CONTROLS

    // ----> SECTION END: AUXILIARY MODAL WINDOWS

    // ----> SECTION START: BUTTONS AND SELECTABLES
    /**

    // ----> SECTION END: BUTTONS AND SELECTABLES

    // ----> SECTION START: OTHER UTILITY METHODS
    /**
     * Shows a text with a label using the standard two columns display for the inputs.
     *
     * @param label Label for the text
     * @param text  Text to be displayed
     */
    public static void textLabel(String label, String text) {
        ImGui.pushID(label);

        ImGui.columns(2);
        ImGui.setColumnWidth(0, (ImGui.getWindowWidth()) / 3);
        ImGui.textWrapped(label);
        ImGui.nextColumn();

        ImGui.textWrapped(text);
        ImGui.columns(1);
        ImGui.popID();
    }

    /**
     * Calculates the given text width for the current font
     *
     * @param text The text from which the width is going to be calculated
     * @return The size of the text
     */
    public static float textSize(String text) {
        return ImGui.getFont().calcTextSizeAX(ImGui.getFontSize(), ImGui.getWindowSizeX(), ImGui.getWindowSizeX(), text);
    }

    /**
     * Sets the ImGUI cursor to be aligned on both axis given the desired alignment and total size of the elements to be
     * aligned on both axis, within the window the function is called.
     *
     * @param alignX Enum value of the horizontal alignment
     * @param alignY Enum value of the vertical alignment
     * @param sizeX  Size on the horizontal axis to offset the cursor
     * @param sizeY  Size on the vertical axis to offset the cursor
     */
    public static void align(AlignX alignX, AlignY alignY, float sizeX, float sizeY) {

        ImGui.setCursorPos(0, 0);
        float x = 0f;
        float y = 0f;
        float titleBarY = ImGui.getFrameHeightWithSpacing();
        float regionX = ImGui.getWindowSizeX();
        float regionY = ImGui.getWindowSizeY();

        switch (alignX) {
            case LEFT:
                x = ImGui.getStyle().getWindowPaddingX();
                break;
            case CENTER:
                x = regionX / 2 - sizeX / 2;
                break;
            case RIGHT:
                x = regionX - sizeX - ImGui.getStyle().getWindowPaddingX();
                break;
        }

        switch (alignY) {
            case TOP:
                y = titleBarY;
                break;
            case CENTER:
                y = regionY / 2 - sizeY / 2;
                break;
            case BOTTOM:
                y = regionY - sizeY - ImGui.getStyle().getWindowPaddingY();
                break;
        }

        ImGui.setCursorPos(x, y);
    }

    /**
     * For windows. Sets the ImGUI cursor to be aligned on both axis given the desired alignment and total size of the elements to be
     * aligned on both axis, within the window the function is called.
     *
     * @param alignX Enum value of the horizontal alignment
     * @param alignY Enum value of the vertical alignment
     * @param sizeX  Size on the horizontal axis to offset the cursor
     * @param sizeY  Size on the vertical axis to offset the cursor
     */
    public static void alignNoHeader(AlignX alignX, AlignY alignY, float sizeX, float sizeY) {

        ImGui.setCursorPos(0, 0);
        float x = 0f;
        float y = 0f;
        float regionX = ImGui.getWindowSizeX();
        float regionY = ImGui.getWindowSizeY();

        switch (alignX) {
            case LEFT:
                x = ImGui.getStyle().getWindowPaddingX();
                break;
            case CENTER:
                x = regionX / 2 - sizeX / 2;
                break;
            case RIGHT:
                x = regionX - sizeX - ImGui.getStyle().getWindowPaddingX();
                break;
        }

        switch (alignY) {
            case TOP:
                y = 0;
                break;
            case CENTER:
                y = regionY / 2 - sizeY / 2;
                break;
            case BOTTOM:
                y = regionY - sizeY - ImGui.getStyle().getWindowPaddingY();
                break;
        }

        ImGui.setCursorPos(x, y);
    }
    // ----> SECTION END: OTHER UTILITY METHODS
}


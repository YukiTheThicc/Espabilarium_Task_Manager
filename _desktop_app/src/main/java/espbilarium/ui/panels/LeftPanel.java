package espbilarium.ui.panels;

import espbilarium.ui.ImGuiLayer;
import espbilarium.utils.Resources;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;

/**
 * LeftPanel
 *
 * @author Santiago Barreiro
 */
public class LeftPanel extends Panel {

    // CONSTANTS


    // ATTRIBUTES


    // CONSTRUCTORS
    public LeftPanel() {
        super("left_panel", Resources.literal("left_panel"));
        setFlags(ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize);
    }


    // GETTERS & SETTERS


    // METHODS
    @Override
    public void renderPanel(ImGuiLayer layer) {
        ImGui.begin(this.getTitle() + "###" + this.getId(), this.getFlags());
        ImGui.text("This is the left panel!");

        ImGui.button("Tasks");
        ImGui.end();
    }
}

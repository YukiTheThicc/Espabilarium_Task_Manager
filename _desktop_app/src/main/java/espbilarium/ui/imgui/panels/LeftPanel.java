package espbilarium.ui.imgui.panels;

import espbilarium.Espabilarium;
import espbilarium.ui.imgui.ImGuiLayer;
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
        super("left_panel", Espabilarium.getLiteral("left_panel"));
        setFlags(ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize);
    }


    // GETTERS & SETTERS


    // METHODS
    @Override
    public void renderPanel(ImGuiLayer layer) {
        if (ImGui.begin(this.getTitle() + "###" + this.getId(), this.getFlags())) {
            ImGui.text("This is the left panel!");

            ImGui.end();
        }
    }
}

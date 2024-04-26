package espbilarium.ui.panels;

import espbilarium.utils.Resources;
import espbilarium.ui.ImGuiLayer;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;

/**
 * DashboardPanel
 *
 * @author Santiago Barreiro
 */
public class DashboardPanel extends Panel {

    // CONSTANTS


    // ATTRIBUTES


    // CONSTRUCTORS
    public DashboardPanel() {
        super("dashboard", Resources.literal("dashboard"));
        setFlags(ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize);
    }

    // GETTERS & SETTERS


    // METHODS
    @Override
    public void renderPanel(ImGuiLayer layer) {
        ImGui.begin(this.getTitle() + "###" + this.getId(), this.getFlags());
        ImGui.text("This is the dashboard!");
        ImGui.end();
    }
}

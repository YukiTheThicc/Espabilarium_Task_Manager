package ui.imgui.panels;

import imgui.ImGui;
import ui.imgui.ImGuiLayer;

/**
 * SamplePanel
 *
 * @author Santiago Barreiro
 */
public class SamplePanel extends Panel {

    // CONSTRUCTORS
    public SamplePanel(String id, String title) {
        super(id, title);
    }

    // METHODS
    @Override
    public void renderPanel(ImGuiLayer layer) {
        if (ImGui.begin(this.getTitle())) {
            ImGui.text("Hello Espabilarium! This is a sample panel");
            ImGui.end();
        }
    }
}

package esp.ui.windows;

import imgui.ImGui;
import esp.ui.ImGuiLayer;

/**
 * SamplePanel
 *
 * @author Santiago Barreiro
 */
public class SampleWindow extends Window {

    // CONSTRUCTORS
    public SampleWindow(String id, String title) {
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

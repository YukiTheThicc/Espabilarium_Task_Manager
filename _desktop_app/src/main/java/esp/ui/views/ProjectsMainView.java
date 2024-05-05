package esp.ui.views;

import esp.ui.UserInterface;
import imgui.ImGui;

/**
 * ProjectsMainView
 *
 * @author: Santiago Barreiro
 */
public class ProjectsMainView {

    // METHODS
    public static void render() {
        if (ImGui.begin("##projects", UserInterface.MAIN_VIEW_FLAGS)) {
            ImGui.text("this is the projects view!");
            ImGui.end();
        }
    }
}

package frontend.ui.views;

import frontend.ui.UserInterface;
import imgui.ImGui;

/**
 * ArchiveMainView
 *
 * @author: Santiago Barreiro
 */
public class ArchiveMainView {

    // METHODS
    public static void render() {
        if (ImGui.begin("##archive", UserInterface.MAIN_VIEW_FLAGS)) {
            ImGui.text("this is the archive view!");
            ImGui.end();
        }
    }
}

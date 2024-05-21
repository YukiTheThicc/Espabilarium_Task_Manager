package frontend.ui.views;

import frontend.ui.UserInterface;
import imgui.ImGui;

/**
 * DashBoardMainView
 *
 * @author: Santiago Barreiro
 */
public class DashboardMainView {

    // METHODS
    public static void render() {

        /* ImGui.begin("##dashboard", OUTER_WINDOW_FLAGS);

        // Setup dashboard docking space
        int dashDock = ImGui.getID("dashboardDock");
        ImGui.dockSpace(dashDock);

        ImGui.end();*/

        if (ImGui.begin("##dashboard", UserInterface.MAIN_VIEW_FLAGS)) {
            ImGui.text("this is the dashboard!");
            ImGui.end();
        }
    }
}

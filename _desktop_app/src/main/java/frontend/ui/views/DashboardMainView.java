package frontend.ui.views;

import frontend.ui.UserInterface;
import frontend.utils.ImGuiUtils;
import frontend.utils.Resources;
import imgui.ImGui;
import imgui.type.ImInt;
import imgui.type.ImString;

/**
 * DashBoardMainView
 *
 * @author: Santiago Barreiro
 */
public class DashboardMainView {

    static ImString string = new ImString("");
    static String result = "";
    static int a = 0;

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

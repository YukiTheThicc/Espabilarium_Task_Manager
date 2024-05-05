package esp.ui.views;

import esp.ui.UserInterface;
import esp.utils.Resources;
import imgui.ImGui;
import imgui.flag.ImGuiTabBarFlags;

/**
 * ProjectsMainView
 *
 * @author: Santiago Barreiro
 */
public class ProjectsMainView {

    // CONSTANTS
    private static final int TAB_BAR_FLAGS = ImGuiTabBarFlags.NoCloseWithMiddleMouseButton;

    // METHODS
    public static void render() {
        if (ImGui.begin("##projects", UserInterface.MAIN_VIEW_FLAGS)) {
            ImGui.text("this is the projects view!");
            if (ImGui.beginTabBar("projectsTabBar", TAB_BAR_FLAGS)) {
                if (ImGui.beginTabItem(Resources.literal("overview"))) {
                    ImGui.endTabItem();
                }
                ImGui.endTabBar();
            }
            ImGui.end();
        }
    }
}

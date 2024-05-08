package esp.ui.views;

import esp.api.ITask;
import esp.ui.AlignX;
import esp.ui.AlignY;
import esp.ui.Image;
import esp.ui.UserInterface;
import esp.ui.widgets.ImageButton;
import esp.utils.ImGuiUtils;
import esp.utils.Resources;
import imgui.ImGui;
import imgui.flag.ImGuiComboFlags;
import imgui.flag.ImGuiTabBarFlags;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ProjectsMainView
 *
 * @author: Santiago Barreiro
 */
public class ProjectsMainView {

    // CONSTANTS
    private static final int TAB_BAR_FLAGS = ImGuiTabBarFlags.NoCloseWithMiddleMouseButton;
    private static final int COLUMN_FILTER_FLAGS = ImGuiComboFlags.HeightLargest | ImGuiComboFlags.PopupAlignLeft
            | ImGuiComboFlags.NoArrowButton;

    // ATTRIBUTES
    private final ImageButton createTaskButton;
    private final HashMap<String, Boolean> fieldFilter;
    private final ArrayList<ITask> tasks;

    // CONSTRUCTORS
    public ProjectsMainView() {
        this.createTaskButton = new ImageButton((Image) Resources.icon("create.png"), Resources.literal("create_new_task"), 24f, 24f);
        this.fieldFilter = new HashMap<>();
        this.tasks = new ArrayList<>();
        fieldFilter.put("progress", false);
        fieldFilter.put("state", false);
        fieldFilter.put("priority", false);
    }

    // METHODS
    public void render() {
        if (ImGui.begin("##projects", UserInterface.MAIN_VIEW_FLAGS)) {
            if (ImGui.beginTabBar("projectsTabBar", TAB_BAR_FLAGS)) {
                renderOverview();
                ImGui.endTabBar();
            }
            ImGui.end();
        }
    }

    private void renderOverview() {
        if (ImGui.beginTabItem(Resources.literal("overview"))) {

            ImGui.setCursorPosX(ImGui.getWindowSizeX() - 150f);
            if (ImGui.beginCombo("##", Resources.literal("fields"), COLUMN_FILTER_FLAGS)) {
                for (String field : fieldFilter.keySet()) {
                    if (ImGui.checkbox(field, fieldFilter.get(field))) fieldFilter.put(field, !fieldFilter.get(field));
                }
                ImGui.endCombo();
            }

            //ImGui.setCursorPosX(ImGui.getWindowPosX());
            createTaskButton.render(false);


            if (ImGui.beginChild("##")) {

                if (!tasks.isEmpty()) {
                    if (ImGui.beginTable("##", 5)) {
                        ImGui.tableNextColumn();
                        ImGui.tableHeader("a");
                        ImGui.tableNextColumn();
                        ImGui.tableHeader("b");
                        ImGui.tableNextColumn();
                        ImGui.tableHeader("c");
                        ImGui.tableNextColumn();
                        ImGui.tableHeader("d");
                        ImGui.tableNextColumn();
                        ImGui.tableHeader("e");
                        ImGui.endTable();
                    }
                } else {
                    String message = Resources.literal("no_tasks_found");
                    ImGuiUtils.align(AlignX.CENTER, AlignY.CENTER, ImGuiUtils.textSize(message), ImGui.getFontSize());
                    ImGui.textWrapped(message);
                }
                ImGui.endChild();
            }
            ImGui.endTabItem();
        }
    }
}

package frontend.ui.widgets;

import backend.api.ITask;
import imgui.ImGui;

/**
 * ProjectOverviewCard
 *
 * @author Santiago Barreiro
 */
public class ProjectOverviewCard {

    // METHODS
    public static void render(ITask task) {
        ImGui.pushID(task.getName());
        ImGui.beginGroup();

        float buttonOriginX = ImGui.getCursorPosX();
        float buttonOriginY = ImGui.getCursorPosY();
        ImGui.button("##", ImGui.getContentRegionAvailX(), 64);
        ImGui.setCursorPos(buttonOriginX + ImGui.getStyle().getWindowPaddingX(), buttonOriginY + ImGui.getStyle().getWindowPaddingX());
        ImGui.text(task.getName());

        if (task.getParent() != null) {
            // Print parent info
            ITask parent = task.getParent();
            ImGui.text(parent.getName());
        }

        ImGui.progressBar(task.getProgress());

        ImGui.endGroup();
        ImGui.popID();
    }
}

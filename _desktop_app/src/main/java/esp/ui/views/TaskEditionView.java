package esp.ui.views;

import esp.api.ITask;
import esp.events.EventSystem;
import esp.ui.AlignX;
import esp.ui.AlignY;
import esp.ui.ImGuiLayer;
import esp.utils.EspStyles;
import esp.utils.ImGuiUtils;
import esp.utils.Resources;
import imgui.flag.ImGuiTabItemFlags;
import imgui.internal.ImGui;
import imgui.type.ImString;

/**
 * TaskEditionView
 *
 * @author Santiago Barreiro
 */
public class TaskEditionView extends View {

    // ATTRIBUTES
    private final ITask task;
    private boolean editMode;
    private boolean isDirty;
    private boolean shouldClose;

    // CONSTRUCTORS
    public TaskEditionView(ImGuiLayer layer, EventSystem es, ITask task, boolean isNew) {
        super(layer, es);
        this.task = task;
        this.editMode = isNew;
        this.isDirty = isNew;
        this.shouldClose = false;
    }

    // GETTERS
    public boolean shouldClose() {
        return shouldClose;
    }

    // METHODS
    @Override
    void render() {
        int flags = isDirty ? ImGuiTabItemFlags.UnsavedDocument : ImGuiTabItemFlags.None;
        if (ImGui.beginTabItem(task.getName() + "##" + task.getUuid(), flags)) {

            ImGui.pushFont(getLayer().getBigFont());
            ImGui.textWrapped(task.getName());
            ImGui.separator();
            ImGui.popFont();
            if (editMode) editMode();
            else inspectMode();


            ImGuiUtils.align(AlignX.RIGHT, AlignY.BOTTOM, editMode ? 128f : 64f, EspStyles.SMALL_ICON_SIZE);
            ImGui.separator();
            if (editMode) {
                ImGui.button(Resources.literal("save"), 60f, EspStyles.SMALL_ICON_SIZE);
                ImGui.sameLine();
            }
            if (ImGui.button(Resources.literal("close"), 60f, EspStyles.SMALL_ICON_SIZE)) shouldClose = true;
            ImGui.endTabItem();
        }
    }

    private void editMode() {

        ImString name = new ImString(task.getName());
        ImGuiUtils.textLabel(Resources.literal("uuid"), task.getUuid());
        if (ImGuiUtils.inputText(Resources.literal("name"), name)) {
            task.setName(name.get());
            isDirty = true;
        }
    }

    private void inspectMode() {
        ImGuiUtils.textLabel(Resources.literal("uuid"), task.getUuid());
        ImGuiUtils.textLabel(Resources.literal("name"), task.getName());
    }
}

package esp.ui.views;

import esp.api.IEvent;
import esp.api.ITask;
import esp.events.EventSystem;
import esp.events.UIEvent;
import esp.tasks.Task;
import esp.ui.AlignX;
import esp.ui.AlignY;
import esp.ui.ImGuiLayer;
import esp.ui.Image;
import esp.utils.EspStyles;
import esp.utils.ImGuiUtils;
import esp.utils.Resources;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiTabItemFlags;
import imgui.internal.ImGui;
import imgui.type.ImString;

/**
 * TaskEditionView
 *
 * @author Santiago Barreiro
 */
public class TaskEditionView extends View implements IEvent.Observer {

    // ATTRIBUTES
    private final ITask task;
    private boolean editMode;
    private boolean isDirty;
    private boolean isNewTask;
    private boolean shouldClose;
    private final int[] activeColor;

    // CONSTRUCTORS
    public TaskEditionView(ImGuiLayer layer, EventSystem es, ITask task, boolean isNew) {
        super(layer, es);
        this.task = task.copy(task.getUuid());
        this.editMode = isNew;
        this.isDirty = isNew;
        this.isNewTask = isNew;
        this.shouldClose = false;
        this.activeColor = Resources.color("accent");
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

            // Title Bar
            ImGui.pushFont(getLayer().getBigFont());
            ImGui.textWrapped(task.getName());
            ImGui.sameLine();
            ImGui.setCursorPosX(ImGui.getCursorPosX() + ImGui.getContentRegionAvailX() - EspStyles.SMALL_ICON_SIZE - ImGui.getStyle().getFramePaddingX() * 2);
            Image closeCross = (Image) Resources.icon("left_arrow");
            if (closeCross != null && imgui.ImGui.imageButton(closeCross.getId(), EspStyles.SMALL_ICON_SIZE, EspStyles.SMALL_ICON_SIZE)) shouldClose = true;
            ImGui.separator();
            ImGui.popFont();

            if (editMode) editMode();
            else inspectMode();
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

        imgui.ImGui.pushStyleColor(ImGuiCol.Button, activeColor[0], activeColor[1], activeColor[2], activeColor[3]);
        ImGuiUtils.align(AlignX.RIGHT, AlignY.BOTTOM, 128f, EspStyles.SMALL_ICON_SIZE);
        if (ImGui.button(Resources.literal("save"), 60f, EspStyles.SMALL_ICON_SIZE)) {
            getEventSystem().throwEvent(new UIEvent(isNewTask ? UIEvent.Type.CREATE_TASK : UIEvent.Type.SAVE_TASK, this.task, 1));
            isNewTask = false;
        }
        ImGui.sameLine();
        if (ImGui.button(Resources.literal("cancel"), 60f, EspStyles.SMALL_ICON_SIZE)) {
            if (isNewTask) shouldClose = true;
            else editMode = false;
        }
        ImGui.popStyleColor();
    }

    private void inspectMode() {
        ImGuiUtils.textLabel(Resources.literal("uuid"), task.getUuid());
        ImGuiUtils.textLabel(Resources.literal("name"), task.getName());
    }

    @Override
    public void handleEvent(IEvent event) {

    }
}

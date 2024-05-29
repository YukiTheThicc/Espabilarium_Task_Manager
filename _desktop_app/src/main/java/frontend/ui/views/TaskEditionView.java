package frontend.ui.views;

import backend.api.IEventSystem;
import backend.api.ITask;
import backend.events.EventSystem;
import frontend.events.Event;
import frontend.ui.AlignX;
import frontend.ui.AlignY;
import frontend.ui.ImGuiLayer;
import frontend.ui.Image;
import frontend.utils.EspStyles;
import frontend.utils.ImGuiUtils;
import frontend.utils.Resources;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
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
    private boolean isNewTask;
    private boolean shouldClose;
    private boolean isWaiting;
    private final int[] activeColor;

    // CONSTRUCTORS
    public TaskEditionView(ImGuiLayer layer, IEventSystem es, ITask task, boolean isNew) {
        super(layer, es);
        this.task = task.copy(task.getUuid());
        this.editMode = isNew;
        this.isDirty = isNew;
        this.isNewTask = isNew;
        this.shouldClose = false;
        this.isWaiting = false;
        this.activeColor = Resources.color("accent");
    }

    // GETTERS
    public boolean shouldClose() {
        return shouldClose;
    }

    public ITask getTask() {
        return task;
    }

    // METHODS
    @Override
    public void render() {
        int flags = isDirty ? ImGuiTabItemFlags.UnsavedDocument : ImGuiTabItemFlags.None;
        if (ImGui.beginTabItem(task.getName() + "###" + task.getUuid(), flags)) {

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

    public void taskSaved() {
        this.isWaiting = false;
        this.isDirty = false;
    }

    private void editMode() {

        ImVec2 origin = ImGui.getCursorScreenPos();
        ImVec2 available = ImGui.getContentRegionAvail();
        ImGuiUtils.textLabel(Resources.literal("uuid"), task.getUuid());
        ImString newName = new ImString(task.getName(), 256);
        if (ImGuiUtils.inputText(Resources.literal("name"), newName)) {
            task.setName(newName.get());
            isDirty = true;
        }

        imgui.ImGui.pushStyleColor(ImGuiCol.Button, activeColor[0], activeColor[1], activeColor[2], activeColor[3]);
        ImGuiUtils.align(AlignX.RIGHT, AlignY.BOTTOM, 128f, EspStyles.SMALL_ICON_SIZE);
        if (ImGui.button(Resources.literal("save"), 60f, EspStyles.SMALL_ICON_SIZE)) {
            if (isNewTask) getEventSystem().throwEvent(new Event(Event.Type.STOW_TASK, this.task, 1));
            getEventSystem().throwEvent(new Event(Event.Type.UPDATE_TASK, this.task, 1));
            isWaiting = true;
            isNewTask = false;
        }
        ImGui.sameLine();
        if (ImGui.button(Resources.literal("cancel"), 60f, EspStyles.SMALL_ICON_SIZE)) {
            if (isNewTask) shouldClose = true;
            else editMode = false;
        }

        if (isWaiting) {
            ImGui.setNextWindowBgAlpha(0.5f);
            ImGui.setNextWindowPos(origin.x, origin.y);
            ImGui.beginChild("waiting_overlay", available.x, available.y);
            String message = Resources.literal("saving");
            ImGuiUtils.align(AlignX.CENTER, AlignY.CENTER, ImGuiUtils.textSize(message), ImGui.getFontSize());
            ImGui.textWrapped(message);
            ImGui.endChild();
        }

        ImGui.popStyleColor();
    }

    private void inspectMode() {
        ImGuiUtils.textLabel(Resources.literal("uuid"), task.getUuid());
        ImGuiUtils.textLabel(Resources.literal("name"), task.getName());
    }
}

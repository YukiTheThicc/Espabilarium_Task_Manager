package esp.ui.views;

import esp.api.ITask;
import esp.api.ITaskStowage;
import esp.events.UIEvent;
import esp.events.EventSystem;
import esp.tasks.Task;
import esp.tasks.TaskQueryMaker;
import esp.ui.*;
import esp.ui.widgets.ImageButton;
import esp.utils.ImGuiUtils;
import esp.utils.Resources;
import imgui.ImGui;
import imgui.flag.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ProjectsMainView
 *
 * @author: Santiago Barreiro
 */
public class ProjectsMainView extends View {

    // CONSTANTS
    private static final int TAB_BAR_FLAGS = ImGuiTabBarFlags.NoCloseWithMiddleMouseButton;
    private static final int COLUMN_FILTER_FLAGS = ImGuiComboFlags.HeightLargest | ImGuiComboFlags.PopupAlignLeft
            | ImGuiComboFlags.NoArrowButton;
    private static final int TABLE_FLAGS = ImGuiTableFlags.Sortable;

    // ATTRIBUTES
    private final ImageButton createTaskButton;
    private final TaskQueryMaker queryMaker;
    private final ArrayList<Field> activeFields;
    private final HashMap<Field, Boolean> fieldFilter;
    private final ArrayList<ITask> tasks;

    // CONSTRUCTORS
    public ProjectsMainView(EventSystem es, TaskQueryMaker queryMaker) {
        super(es);
        float buttonWidth = ImGuiUtils.textSize(Resources.literal("create_new_task")) + 24f + ImGui.getStyle().getFramePaddingX() * 6;
        this.createTaskButton = new ImageButton((Image) Resources.icon("create.png"), Resources.literal("create_new_task"),
                24f, 24f, buttonWidth);
        this.queryMaker = queryMaker;
        this.activeFields = new ArrayList<>();
        this.fieldFilter = new HashMap<>();
        this.tasks = new ArrayList<>();

        for (Field field : Task.class.getDeclaredFields()) {
            if (!field.getName().equals("uuid") && !field.getName().equals("name") && !field.getName().equals("children")
                    && !field.getName().equals("parent")) {
                fieldFilter.put(field, false);
            }
        }
    }

    // METHODS
    public void render() {
        if (ImGui.begin("##projects", UserInterface.MAIN_VIEW_FLAGS)) {

            ImGuiUtils.alignNoHeader(AlignX.CENTER, AlignY.TOP, createTaskButton.getWidth(), 24f);
            if (createTaskButton.render(false)) {
                this.getEventSystem().throwEvent(new UIEvent(UIEvent.Type.CREATE_TASK));
                this.updateList();
            }

            if (ImGui.beginTabBar("projectsTabBar", TAB_BAR_FLAGS)) {
                renderOverview();
                ImGui.endTabBar();
            }
            ImGui.end();
        }
    }

    private void renderTaskRow(ITask task) {
        ImGui.tableNextColumn();
        ImGui.text(task.getUuid());
        ImGui.tableNextColumn();
        ImGui.text(task.getName());

        for (Field field : activeFields) {
            ImGui.tableNextColumn();
            Object value = ((Task) task).getField(field);
            if (value != null) ImGui.text(value.toString());
        }
    }

    private void updateList() {
        tasks.clear();
        tasks.addAll(queryMaker.queryTasks("name", ITaskStowage.QueryMaker.Order.DESCENDANT));
    }

    private void updateTable(Field changed) {
        if (fieldFilter.get(changed)) activeFields.remove(changed);
        else activeFields.add(changed);
        fieldFilter.put(changed, !fieldFilter.get(changed));
    }

    private void renderOverview() {
        if (ImGui.beginTabItem(Resources.literal("overview"))) {

            ImGuiUtils.alignNoHeader(AlignX.RIGHT, AlignY.TOP, 150, ImGui.getFontSize() + ImGui.getStyle().getWindowPaddingY() * 2);
            if (ImGui.beginCombo("##", Resources.literal("fields"), COLUMN_FILTER_FLAGS)) {
                for (Field field : fieldFilter.keySet()) {
                    if (ImGui.checkbox(Resources.literal(field.getName()), fieldFilter.get(field))) updateTable(field);
                }
                ImGui.endCombo();
            }

            if (ImGui.beginChild("##")) {

                if (!tasks.isEmpty()) {
                    if (ImGui.beginTable("##", 2 + activeFields.size(), TABLE_FLAGS)) {
                        // Basic fields
                        ImGui.tableNextColumn();
                        ImGui.tableHeader(Resources.literal("uuid"));
                        ImGui.tableNextColumn();
                        ImGui.tableHeader(Resources.literal("name"));

                        for (Field field : fieldFilter.keySet()) {
                            if (fieldFilter.get(field)) {
                                ImGui.tableNextColumn();
                                ImGui.tableHeader(Resources.literal(field.getName()));
                            }
                        }

                        for (ITask task : tasks) {
                            renderTaskRow(task);
                        }

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

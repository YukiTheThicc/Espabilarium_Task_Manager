package esp.ui;

import esp.tasks.Task;
import esp.tasks.TaskPriority;
import esp.tasks.TaskType;
import esp.ui.widgets.ProjectOverviewCard;
import esp.utils.Resources;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.*;
import org.joml.Vector2f;

/**
 * UserInteface
 *
 * @author Santiago Barreiro
 */
public class UserInterface {

    // CONSTANTS
    private static final int OUTER_WINDOW = ImGuiWindowFlags.NoDocking | ImGuiWindowFlags.NoTitleBar |
            ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
            ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus | ImGuiWindowFlags.NoScrollbar;
    private static final int MENU_BAR_PADDING = 24;
    private static final int MENU_BAR_OFFSET = 72;

    // ATTRIBUTES

    // STYLING ATTRIBUTES
    private float menuBarHeight = MENU_BAR_PADDING;
    private float menuBarItemSpacing = 8;
    private ImVec2 itemSpacing;

    // CONSTRUCTORS
    public UserInterface() {

    }

    // METHODS
    public void init() {
        this.menuBarHeight = ImGui.getFontSize() + MENU_BAR_PADDING * 2;
        this.menuBarItemSpacing = (MENU_BAR_PADDING - ImGui.getStyle().getWindowPaddingY()) * 2;
        this.itemSpacing = ImGui.getStyle().getItemSpacing();
    }

    public void render(ImGuiLayer layer) {

        // Background window
        Vector2f windowPos = Window.getPosition();
        ImGui.setNextWindowPos(windowPos.x, windowPos.y);
        ImGui.setNextWindowSize(Window.getWidth(), Window.getHeight());

        ImGui.begin("##outer", OUTER_WINDOW | ImGuiWindowFlags.MenuBar);
        renderMenuBar();
        ImGui.end();

        renderLeftPanel(windowPos);
        renderDashBoard(windowPos);
    }

    private void renderMenuBar() {

        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0, MENU_BAR_PADDING);
        ImGui.getStyle().setItemSpacing(itemSpacing.x, menuBarItemSpacing);

        Object image = Resources.icon("default.png");
        if (image instanceof Image) {
            ImGui.image(((Image)image).getId(), 24f, 24f);
        }
        if (ImGui.beginMainMenuBar()) {
            ImGui.setCursorPosX(MENU_BAR_OFFSET);
            ImGui.menuItem("File");
            ImGui.endMainMenuBar();
        }
        ImGui.getStyle().setItemSpacing(itemSpacing.x, itemSpacing.y);
        ImGui.popStyleVar();
    }

    /**
     * Renders the left panel of the main view
     * @param windowPos The current position of the window
     */
    private void renderLeftPanel(Vector2f windowPos) {

        ImGui.setNextWindowPos(windowPos.x, windowPos.y + menuBarHeight);
        ImGui.setNextWindowSize(Window.getWidth() * 0.25f, Window.getHeight() - menuBarHeight);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 2);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 8);

        ImGui.begin("##leftPanel", OUTER_WINDOW);
        ImGui.textWrapped("This is the left panel!");

        ProjectOverviewCard.render(new Task("001", "Task 1", TaskType.TASK, TaskPriority.LOWEST));
        ProjectOverviewCard.render(new Task("002", "Task 2", TaskType.TASK, TaskPriority.LOWEST));
        ProjectOverviewCard.render(new Task("003", "Task 3", TaskType.TASK, TaskPriority.LOWEST));

        ImGui.end();
        ImGui.popStyleVar(2);
    }

    /**
     * Renders the main view dashboard with a docking area
     * @param windowPos The current position of the window
     */
    private void renderDashBoard(Vector2f windowPos) {

        ImGui.setNextWindowPos(windowPos.x + Window.getWidth() * 0.25f, windowPos.y + menuBarHeight);
        ImGui.setNextWindowSize(Window.getWidth() * 0.75f, Window.getHeight() - menuBarHeight);

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0f, 0f);
        imgui.internal.ImGui.begin("##dashboard", OUTER_WINDOW);

        // Setup dashboard docking space
        int dashDock = ImGui.getID("dashboardDock");
        ImGui.dockSpace(dashDock);

        ImGui.end();
        ImGui.popStyleVar(1);
    }
}

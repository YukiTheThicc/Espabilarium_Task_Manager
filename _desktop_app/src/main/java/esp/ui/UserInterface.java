package esp.ui;

import esp.ui.views.ArchiveMainView;
import esp.ui.views.DashboardMainView;
import esp.ui.views.ProjectsMainView;
import esp.ui.widgets.ImageSelectable;
import esp.utils.Resources;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.*;
import org.joml.Vector2f;

import java.util.ArrayList;

/**
 * UserInteface
 *
 * @author Santiago Barreiro
 */
public class UserInterface {

    public enum MainView {
        DASHBOARD,
        PROJECTS,
        ARCHIVE
    }

    // CONSTANTS
    public static final int MAIN_VIEW_FLAGS = ImGuiWindowFlags.NoDocking | ImGuiWindowFlags.NoTitleBar |
            ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
            ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus | ImGuiWindowFlags.NoScrollbar;
    private static final int MENU_BAR_PADDING = 24;
    private static final int MENU_BAR_OFFSET = 72;
    private static final float LEFT_PANEL_SIZE_FACTOR = 0.2f;

    // ATTRIBUTES
    private final ArrayList<ImageSelectable> leftPanelOptions;
    private MainView currentView;

    // STYLING ATTRIBUTES
    private float menuBarHeight = MENU_BAR_PADDING;
    private float menuBarItemSpacing = 8;
    private ImVec2 itemSpacing;

    // CONSTRUCTORS
    public UserInterface() {
        this.leftPanelOptions = new ArrayList<>();
    }

    // METHODS
    public void init() {
        this.menuBarHeight = ImGui.getFontSize() + MENU_BAR_PADDING * 2;
        this.menuBarItemSpacing = (MENU_BAR_PADDING - ImGui.getStyle().getWindowPaddingY()) * 2;
        this.itemSpacing = ImGui.getStyle().getItemSpacing();
        this.currentView = MainView.DASHBOARD;

        // Create left panel buttons
        leftPanelOptions.add(new ImageSelectable((Image) Resources.icon("dashboard.png"), Resources.literal("dashboard"),
                48f, 48f, MainView.DASHBOARD));
        leftPanelOptions.add(new ImageSelectable((Image) Resources.icon("projects.png"), Resources.literal("projects"),
                48f, 48f, MainView.PROJECTS));
        leftPanelOptions.add(new ImageSelectable((Image) Resources.icon("archive.png"), Resources.literal("archive"),
                48f, 48f, MainView.ARCHIVE));
    }

    public void render(ImGuiLayer layer) {

        // Background window
        Vector2f windowPos = Window.getPosition();
        ImGui.setNextWindowPos(windowPos.x, windowPos.y);
        ImGui.setNextWindowSize(Window.getWidth(), Window.getHeight());

        ImGui.begin("##outer", MAIN_VIEW_FLAGS | ImGuiWindowFlags.MenuBar);
        renderMenuBar();
        ImGui.end();

        renderLeftPanel(windowPos);
        renderMainPanel(windowPos);
    }

    private void renderMenuBar() {

        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0, MENU_BAR_PADDING);
        ImGui.getStyle().setItemSpacing(itemSpacing.x, menuBarItemSpacing);

        Object image = Resources.icon("default.png");
        if (image instanceof Image) {
            ImGui.image(((Image) image).getId(), 24f, 24f);
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
     *
     * @param windowPos The current position of the window
     */
    private void renderLeftPanel(Vector2f windowPos) {

        ImGui.setNextWindowPos(windowPos.x, windowPos.y + menuBarHeight);
        ImGui.setNextWindowSize(Window.getWidth() * LEFT_PANEL_SIZE_FACTOR, Window.getHeight() - menuBarHeight);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 2);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 8);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, ImGui.getStyle().getItemSpacingX(), ImGui.getStyle().getItemSpacingY() * 4);

        ImGui.begin("##leftPanel", MAIN_VIEW_FLAGS);

        for (ImageSelectable option : leftPanelOptions) {
            if (option != null) {
                if (option.render(option.getCode() == currentView)) currentView = (MainView) option.getCode();
            }
        }

        ImGui.end();
        ImGui.popStyleVar(3);
    }

    /**
     * Renders the CURRENT MAIN VIEW
     *
     * @param windowPos The current position of the window
     */
    private void renderMainPanel(Vector2f windowPos) {

        ImGui.setNextWindowPos(windowPos.x + Window.getWidth() * LEFT_PANEL_SIZE_FACTOR, windowPos.y + menuBarHeight);
        ImGui.setNextWindowSize(Window.getWidth() * (1 - LEFT_PANEL_SIZE_FACTOR), Window.getHeight() - menuBarHeight);

        //ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0f, 0f);

        switch (currentView) {
            case PROJECTS:
                ProjectsMainView.render();
                break;
            case ARCHIVE:
                ArchiveMainView.render();
                break;
            default:
                DashboardMainView.render();
                break;
        }

        //ImGui.popStyleVar(1);
    }
}

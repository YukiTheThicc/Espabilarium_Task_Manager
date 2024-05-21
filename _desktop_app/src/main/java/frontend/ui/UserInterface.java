package frontend.ui;

import backend.events.EventSystem;
import backend.tasks.TaskQueryMaker;
import frontend.ui.views.ArchiveMainView;
import frontend.ui.views.DashboardMainView;
import frontend.ui.views.DebugView;
import frontend.ui.views.ProjectsMainView;
import frontend.ui.widgets.ImageButton;
import frontend.utils.EspStyles;
import frontend.utils.Resources;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.*;
import org.joml.Vector2f;

import java.util.ArrayList;

import static backend.events.Event.Type.*;

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
    private static final int MENU_BAR_PADDING = 24;         // Menu bar padding
    private static final int MENU_BAR_OFFSET = 72;          // Menu bar offset (to place an image before the menu items)
    private static final int LEFT_PANEL_MAX_WIDTH = 266;    // Static width fot the left panel

    // ATTRIBUTES
    private final ArrayList<ImageButton> leftPanelOptions;          // List of button options to render in the left panel
    private final EventSystem eventSystem;                          // Event system to throw GUI generated events
    private final TaskQueryMaker queryMaker;                        // 
    private ProjectsMainView projectsMainView;                      // Projects main view object
    private DebugView debugView;                                    // Projects main view object
    private MainView currentView;                                   // Current view to display in the main view section
    private boolean collapsedLP = false;                            // Flag that represents if the left panel is collapsed
    private final boolean debug;                                    // Debug mode flag

    // STYLING ATTRIBUTES
    private float menuBarHeight = MENU_BAR_PADDING;                 // Height of the menu bar
    private float menuBarItemSpacing = 8;                           // Spacing for the items in the menu bar
    private ImVec2 itemSpacing;                                     // Normal spacing for the rest of the items
    private float currentLPWidth = LEFT_PANEL_MAX_WIDTH;            // Current width for the left panel
    private float minLPWidth = LEFT_PANEL_MAX_WIDTH;                // Minimum width of the left panel


    // CONSTRUCTORS
    public UserInterface(EventSystem es, TaskQueryMaker queryMaker, boolean debugMode) {
        this.leftPanelOptions = new ArrayList<>();
        this.eventSystem = es;
        this.queryMaker = queryMaker;
        this.debug = debugMode;
    }

    // METHODS

    /**
     * Initializes the User interface elements
     */
    public void init(ImGuiLayer layer) {

        this.minLPWidth = EspStyles.BIG_ICON_SIZE + ImGui.getStyle().getFramePaddingX() * 6;
        this.menuBarHeight = ImGui.getFontSize() + MENU_BAR_PADDING * 2;
        this.menuBarItemSpacing = (MENU_BAR_PADDING - ImGui.getStyle().getWindowPaddingY()) * 2;
        this.itemSpacing = ImGui.getStyle().getItemSpacing();
        this.currentView = MainView.DASHBOARD;

        // Create views
        this.projectsMainView = new ProjectsMainView(layer, eventSystem, queryMaker);
        eventSystem.attachObserver(new Enum[]{SAVED_TASK, CREATED_TASK}, this.projectsMainView);
        this.debugView = new DebugView(layer, eventSystem);

        // Attach the debug view to the event system
        //if (debug) eventSystem.attachObserver(null, debugView);

        // Create left panel buttons
        leftPanelOptions.add(new ImageButton((Image) Resources.icon("dashboard.png"), Resources.literal("dashboard"),
                EspStyles.BIG_ICON_SIZE, EspStyles.BIG_ICON_SIZE, MainView.DASHBOARD));
        leftPanelOptions.add(new ImageButton((Image) Resources.icon("projects.png"), Resources.literal("projects"),
                EspStyles.BIG_ICON_SIZE, EspStyles.BIG_ICON_SIZE, MainView.PROJECTS));
        leftPanelOptions.add(new ImageButton((Image) Resources.icon("archive.png"), Resources.literal("archive"),
                EspStyles.BIG_ICON_SIZE, EspStyles.BIG_ICON_SIZE, MainView.ARCHIVE));
    }

    public void render(float dt) {

        // Background window
        Vector2f windowPos = Window.getPosition();
        ImGui.setNextWindowPos(windowPos.x, windowPos.y);
        ImGui.setNextWindowSize(Window.getWidth(), Window.getHeight());

        ImGui.begin("##outer", MAIN_VIEW_FLAGS | ImGuiWindowFlags.MenuBar);
        renderMenuBar();
        ImGui.end();

        renderLeftPanel(windowPos, dt);
        renderMainPanel(windowPos);

        if (debug) debugView.render();
    }

    private void renderMenuBar() {

        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0, MENU_BAR_PADDING);
        ImGui.getStyle().setItemSpacing(itemSpacing.x, menuBarItemSpacing);

        Object image = Resources.icon("default.png");
        if (image instanceof Image) {
            ImGui.image(((Image) image).getId(), EspStyles.SMALL_ICON_SIZE, EspStyles.SMALL_ICON_SIZE);
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
    private void renderLeftPanel(Vector2f windowPos, float dt) {

        ImGui.setNextWindowPos(windowPos.x, windowPos.y + menuBarHeight);
        ImGui.setNextWindowSize(currentLPWidth, Window.getHeight() - menuBarHeight);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 2);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 8);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, ImGui.getStyle().getItemSpacingX(), ImGui.getStyle().getItemSpacingY() * 4);

        ImGui.begin("##leftPanel", MAIN_VIEW_FLAGS);

        for (ImageButton option : leftPanelOptions) {
            if (option != null) {
                if (option.render(option.getCode() == currentView, collapsedLP))
                    currentView = (MainView) option.getCode();
            }
        }

        ImGui.setCursorPos(ImGui.getWindowSizeX() - EspStyles.BIG_ICON_SIZE - ImGui.getStyle().getFramePaddingX(),
                ImGui.getWindowSizeY() - EspStyles.BIG_ICON_SIZE - ImGui.getStyle().getFramePaddingY());
        Image leftArrow = (Image) Resources.icon("left_arrow");
        if (leftArrow != null && ImGui.imageButton(leftArrow.getId(), EspStyles.SMALL_ICON_SIZE, EspStyles.SMALL_ICON_SIZE))
            collapsedLP = !collapsedLP;
        else if (ImGui.button(Resources.literal("collapse_panel"))) collapsedLP = !collapsedLP;

        float normCurrentLPWidth = currentLPWidth / LEFT_PANEL_MAX_WIDTH;
        if (collapsedLP) {
            if (currentLPWidth > minLPWidth) currentLPWidth -= Math.pow(normCurrentLPWidth, 2) / (dt * 5);
        } else if (currentLPWidth < LEFT_PANEL_MAX_WIDTH) {
            currentLPWidth += (1 - Math.pow(normCurrentLPWidth, 2)) / (dt * 5);
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

        ImGui.setNextWindowPos(windowPos.x + currentLPWidth, windowPos.y + menuBarHeight);
        ImGui.setNextWindowSize(Window.getWidth() - currentLPWidth, Window.getHeight() - menuBarHeight);

        switch (currentView) {
            case PROJECTS -> projectsMainView.render();
            case ARCHIVE -> ArchiveMainView.render();
            default -> DashboardMainView.render();
        }
    }
}

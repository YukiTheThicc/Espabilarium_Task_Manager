package espbilarium.ui.imgui;

import espbilarium.ui.Window;
import espbilarium.ui.imgui.panels.DashboardPanel;
import espbilarium.ui.imgui.panels.LeftPanel;
import espbilarium.ui.imgui.panels.Panel;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImGuiWindowClass;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import org.joml.Vector2f;

/**
 * UserInteface
 *
 * @author Santiago Barreiro
 */
public class UserInterface {

    // CONSTANTS
    private static final int OUTER_DOCK_FLAGS = ImGuiWindowFlags.NoDocking | ImGuiWindowFlags.NoTitleBar |
            ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
            ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

    // ATTRIBUTES
    private final Panel leftPanel;
    private final Panel dashboard;
    private final ImGuiLayer layer;
    private final ImInt dockId;
    private final ImInt leftPanelSlot;
    private final ImInt mainPanelSlot;
    private ImGuiWindowClass slotsClass;
    private boolean dockSpaceReady = false;

    // CONSTRUCTORS
    public UserInterface(ImGuiLayer layer) {
        this.leftPanel = new LeftPanel();
        this.dashboard = new DashboardPanel();
        this.layer = layer;
        this.dockId = new ImInt(0);
        this.leftPanelSlot = new ImInt(0);
        this.mainPanelSlot = new ImInt(0);
        this.slotsClass = new ImGuiWindowClass();
        slotsClass.setDockNodeFlagsOverrideSet(ImGuiDockNodeFlags.AutoHideTabBar);
    }

    // METHODS
    public void setupDockSpace() {

        Vector2f windowSize = new Vector2f(Window.getWidth(), Window.getHeight());
        Vector2f windowPos = Window.getPosition();

        imgui.internal.ImGui.dockBuilderRemoveNode(dockId.get());
        imgui.internal.ImGui.dockBuilderAddNode(dockId.get());
        imgui.internal.ImGui.dockBuilderSetNodeSize(dockId.get(), windowSize.x, windowSize.y);
        imgui.internal.ImGui.dockBuilderSetNodePos(dockId.get(), windowPos.x, windowPos.y);

        imgui.internal.ImGui.dockBuilderSplitNode(dockId.get(), ImGuiDir.Left, 0.3f, leftPanelSlot,mainPanelSlot);
        imgui.internal.ImGui.dockBuilderDockWindow(leftPanel.getTitle() + "###" + leftPanel.getId(), leftPanelSlot.get());
        imgui.internal.ImGui.dockBuilderDockWindow(dashboard.getTitle() + "###" + dashboard.getId(), mainPanelSlot.get());
        imgui.internal.ImGui.dockBuilderFinish(dockId.get());

        dockSpaceReady = true;
    }

    public void render() {

        // Set outer dock
        Vector2f windowPos = Window.getPosition();
        imgui.internal.ImGui.setNextWindowPos(windowPos.x, windowPos.y);
        imgui.internal.ImGui.setNextWindowSize(Window.getWidth(), Window.getHeight());
        imgui.internal.ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);
        imgui.internal.ImGui.begin("Dockspace Outer", new ImBoolean(true), OUTER_DOCK_FLAGS);
        imgui.internal.ImGui.popStyleVar();
        dockId.set(ImGui.dockSpace(ImGui.getID("Dockspace")));
        ImGui.end();

        if (!dockSpaceReady) setupDockSpace();
        ImGui.setNextWindowClass(slotsClass);
        leftPanel.renderPanel(layer);
        ImGui.setNextWindowClass(slotsClass);
        dashboard.renderPanel(layer);


    }
}

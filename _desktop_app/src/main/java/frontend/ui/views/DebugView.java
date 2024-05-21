package frontend.ui.views;

import backend.api.IEvent;
import backend.events.EventSystem;
import frontend.ui.ImGuiLayer;
import frontend.ui.Image;
import frontend.ui.Window;
import frontend.utils.EspStyles;
import frontend.utils.Resources;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

import java.util.ArrayList;

/**
 * DebugView
 *
 * @author Santiago Barreiro
 */
public class DebugView extends View implements IEvent.Observer {

    // CONSTANTS
    private static final int VIEW_FLAGS = ImGuiWindowFlags.NoDocking | ImGuiWindowFlags.NoTitleBar |
            ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
            ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus | ImGuiWindowFlags.NoScrollbar;

    // ATTRIBUTES
    private boolean collapsedDebug = false;                         // Collapsed debug window flag
    private final ArrayList<String> debugLog;                       // Collapsed debug window flag

    // CONSTRUCTORS
    public DebugView(ImGuiLayer layer, EventSystem es) {
        super(layer, es);
        this.debugLog = new ArrayList<>();
    }

    // METHODS
    @Override
    public void render() {
        float height = collapsedDebug ? EspStyles.SMALL_ICON_SIZE + ImGui.getStyle().getWindowPaddingY() * 2 : 300f;
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 2);
        ImGui.setNextWindowPos(Window.getPosition().x, Window.getPosition().y + Window.getHeight() - height);

        ImGui.setNextWindowSize(Window.getWidth(), height);
        ImGui.setNextWindowBgAlpha(0.33f);
        Image debug = (Image) Resources.icon("debug");
        if (ImGui.begin("##debuggingWindow", VIEW_FLAGS)) {

            if (debug != null && ImGui.imageButton(debug.getId(), EspStyles.SMALL_ICON_SIZE, EspStyles.SMALL_ICON_SIZE))
                collapsedDebug = !collapsedDebug;

            ImGui.beginChild("##log");
            for (String entry : debugLog) {
                ImGui.text(entry);
            }
            ImGui.endChild();
            ImGui.end();
        }
        ImGui.popStyleVar(1);
    }

    @Override
    public void handleEvent(IEvent event) {
        this.debugLog.add("[" + event.getEventType() + "]");
    }
}

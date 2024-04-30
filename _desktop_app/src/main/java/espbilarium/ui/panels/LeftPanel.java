package espbilarium.ui.panels;

import espbilarium.ui.ImGuiLayer;
import espbilarium.ui.Image;
import espbilarium.ui.widgets.ImageLabelButton;
import espbilarium.utils.EspStyles;
import espbilarium.utils.ImGuiUtils;
import espbilarium.utils.Resources;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

/**
 * LeftPanel
 *
 * @author Santiago Barreiro
 */
public class LeftPanel extends Panel {

    // CONSTANTS


    // ATTRIBUTES
    private final ImageLabelButton createTaskButton;


    // CONSTRUCTORS
    public LeftPanel() {
        super("left_panel", Resources.literal("left_panel"));
        setFlags(ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize);
        Object icon = Resources.icon("xxx.png");
        if (icon instanceof Image) {
            createTaskButton = new ImageLabelButton((Image) icon, Resources.literal("create_new_task"), EspStyles.MEDIUM_ICON_SIZE, EspStyles.MEDIUM_ICON_SIZE);
        } else {
            createTaskButton = new ImageLabelButton(new Image(), Resources.literal("create_new_task"), EspStyles.MEDIUM_ICON_SIZE, EspStyles.MEDIUM_ICON_SIZE);
        }
    }


    // GETTERS & SETTERS


    // METHODS
    @Override
    public void renderPanel(ImGuiLayer layer) {
        ImGui.begin(this.getTitle() + "###" + this.getId(), this.getFlags());
        ImGui.text("This is the left panel!");

        createTaskButton.render();

        ImGui.end();
    }
}

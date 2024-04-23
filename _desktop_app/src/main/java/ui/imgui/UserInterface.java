package ui.imgui;

import ui.imgui.panels.Panel;
import ui.imgui.panels.SamplePanel;

/**
 * UserInteface
 *
 * @author Santiago Barreiro
 */
public class UserInterface {

    // CONSTANTS


    // ATTRIBUTES
    private final Panel defaultPanel;
    private final ImGuiLayer layer;

    // CONSTRUCTORS
    public UserInterface(ImGuiLayer layer) {
        this.defaultPanel = new SamplePanel("sample_panel", "Sample Panel");
        this.layer = layer;
    }


    // GETTERS & SETTERS


    // METHODS
    public void render() {
        defaultPanel.renderPanel(layer);
    }
}

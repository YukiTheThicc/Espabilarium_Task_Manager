package espbilarium.ui.panels;


import imgui.internal.ImGui;

/**
 * MenuBar
 *
 * @author Santiago Barreiro
 */
public class MenuBar {

    public static void horizontalMenuBar() {
       if (ImGui.beginMainMenuBar()) {

           ImGui.endMainMenuBar();
       }
    }
}

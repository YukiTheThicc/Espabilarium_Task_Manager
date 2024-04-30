package esp.utils;

import imgui.flag.ImGuiCol;
import imgui.internal.ImGui;

/**
 * EspStyles
 *
 * @author Santiago Barreiro
 */
public class EspStyles {

    // ICON SIZES VARIABLES
    public static float BIG_ICON_SIZE;
    public static float MEDIUM_ICON_SIZE;
    public static float SMALL_ICON_SIZE;
    public static float BIG_COLUMN_SIZE;
    public static float MEDIUM_COLUMN_SIZE;
    public static float SMALL_COLUMN_SIZE;
    
    public static void setEspStyles(int fontSize) {

        BIG_ICON_SIZE = fontSize * 2.5f;
        MEDIUM_ICON_SIZE = fontSize * 2f;
        SMALL_ICON_SIZE = fontSize * 1.5f;
        BIG_COLUMN_SIZE = fontSize * 30;
        MEDIUM_COLUMN_SIZE = fontSize * 20;
        SMALL_COLUMN_SIZE = fontSize * 10;

        // Colors
        int[] dark_bg = Resources.color("dark_bg");
        int[] main_bg = Resources.color("main_bg");
        int[] inactive = Resources.color("inactive");
        int[] accent = Resources.color("accent");
        int[] highlight = Resources.color("highlight");
        int[] font = Resources.color("font");

        // Windows
        ImGui.getStyle().setColor(ImGuiCol.WindowBg, main_bg[0], main_bg[1], main_bg[2], main_bg[3]);
        ImGui.getStyle().setColor(ImGuiCol.MenuBarBg, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TitleBg, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.ChildBg, dark_bg[0], dark_bg[1], dark_bg[2], dark_bg[3]);
        ImGui.getStyle().setColor(ImGuiCol.Border, dark_bg[0], dark_bg[1], dark_bg[2], dark_bg[3]);
        ImGui.getStyle().setColor(ImGuiCol.PopupBg, dark_bg[0], dark_bg[1], dark_bg[2], dark_bg[3]);
        ImGui.getStyle().setColor(ImGuiCol.Separator, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.SeparatorActive, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.SeparatorHovered, accent[0], accent[1], accent[2], accent[3]);

        // Tabs
        ImGui.getStyle().setColor(ImGuiCol.Tab, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TabActive, highlight[0], highlight[1], highlight[2], highlight[3]);
        ImGui.getStyle().setColor(ImGuiCol.TabHovered, highlight[0], highlight[1], highlight[2], highlight[3]);
        ImGui.getStyle().setColor(ImGuiCol.TabUnfocused, inactive[0], inactive[1], inactive[2], inactive[3]);
        ImGui.getStyle().setColor(ImGuiCol.TabUnfocusedActive, accent[0], accent[1], accent[2], accent[3]);

        // Buttons
        ImGui.getStyle().setColor(ImGuiCol.Button, main_bg[0], main_bg[1], main_bg[2], main_bg[3]);
        ImGui.getStyle().setColor(ImGuiCol.ButtonActive, highlight[0], highlight[1], highlight[2], highlight[3]);
        ImGui.getStyle().setColor(ImGuiCol.ButtonHovered, highlight[0], highlight[1], highlight[2], highlight[3]);

        // Trees
        ImGui.getStyle().setColor(ImGuiCol.Header, main_bg[0], main_bg[1], main_bg[2], main_bg[3]);
        ImGui.getStyle().setColor(ImGuiCol.HeaderActive, highlight[0], highlight[1], highlight[2], highlight[3]);
        ImGui.getStyle().setColor(ImGuiCol.HeaderHovered, highlight[0], highlight[1], highlight[2], highlight[3]);

        // Tables
        ImGui.getStyle().setColor(ImGuiCol.TableHeaderBg, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TableBorderLight, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TableBorderStrong, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TableRowBg, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TableRowBgAlt, accent[0], accent[1], accent[2], accent[3]);

        // Others
        ImGui.getStyle().setColor(ImGuiCol.TitleBg, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgCollapsed, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, highlight[0], highlight[1], highlight[2], highlight[3]);
        ImGui.getStyle().setColor(ImGuiCol.NavHighlight, highlight[0], highlight[1], highlight[2], highlight[3]);
        ImGui.getStyle().setColor(ImGuiCol.Text, font[0], font[1], font[2], font[3]);
        ImGui.getStyle().setColor(ImGuiCol.FrameBg, dark_bg[0], dark_bg[1], dark_bg[2], dark_bg[3]);
        ImGui.getStyle().setColor(ImGuiCol.FrameBgActive, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.FrameBgHovered, accent[0], accent[1], accent[2], accent[3]);
        ImGui.getStyle().setColor(ImGuiCol.TextSelectedBg, accent[0], accent[1], accent[2], accent[3]);

        // STYLES
        // Borders
        ImGui.getStyle().setTabBorderSize(0f);
        ImGui.getStyle().setChildBorderSize(0);
        ImGui.getStyle().setFrameBorderSize(0f);
        ImGui.getStyle().setWindowBorderSize(0f);
        ImGui.getStyle().setPopupBorderSize(0f);

        // Window
        ImGui.getStyle().setChildRounding(0f);
        ImGui.getStyle().setTabRounding(0f);
        ImGui.getStyle().setWindowPadding(8f, 8f);
        ImGui.getStyle().setWindowTitleAlign(0f, 0.5f);
        ImGui.getStyle().setWindowMinSize(10f, 10f);
        ImGui.getStyle().setCellPadding(0, 0);
    }
}

package espbilarium.utils;

import espbilarium.exceptions.EspRuntimeException;
import imgui.flag.ImGuiCol;
import imgui.internal.ImGui;

import java.util.HashMap;

/**
 * EspStyles
 *
 * @author Santiago Barreiro
 */
public class EspStyles {

    private static HashMap<String, int[]> defaultColors() {

        HashMap<String, int[]> colors = new HashMap<>();

        // Dialogger colors
        colors.put("DiaLogger.CRITICAL", new int[]{175, 50, 233, 255});
        colors.put("DiaLogger.ERROR", new int[]{200, 50, 50, 255});
        colors.put("DiaLogger.WARN", new int[]{200, 175, 50, 255});
        colors.put("DiaLogger.DEBUG", new int[]{50, 200, 50, 255});
        colors.put("DiaLogger.SAPP_DEBUG", new int[]{54, 75, 108, 255});

        // main theme
        colors.put("default", new int[]{125, 0, 125, 255});
        colors.put("light_grey", new int[]{101, 110, 123, 255});
        colors.put("grey", new int[]{61, 66, 72, 255});
        colors.put("main_bg", new int[]{41, 46, 52, 255});
        colors.put("dark_bg", new int[]{25, 31, 37, 255});
        colors.put("highlight", new int[]{93, 139, 191, 255});
        colors.put("accent", new int[]{64, 79, 100, 255});
        colors.put("inactive", new int[]{30, 38, 57, 255});

        // Others
        colors.put("font", new int[]{218, 224, 232, 255});
        return colors;
    }

    public static void setEspStyles() {

        HashMap<String, int[]> colors = defaultColors();
        // Colors
        int[] dark_bg = colors.get("dark_bg");
        int[] main_bg = colors.get("main_bg");
        int[] inactive = colors.get("inactive");
        int[] accent = colors.get("accent");
        int[] highlight = colors.get("highlight");
        int[] font = colors.get("font");

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

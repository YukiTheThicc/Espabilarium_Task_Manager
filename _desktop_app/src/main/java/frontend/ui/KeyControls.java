package frontend.ui;

import imgui.ImGuiIO;

import java.util.Arrays;

public class KeyControls {

    private static final int NUM_KEYS = 350;
    private static boolean[] keyPressed = new boolean[NUM_KEYS];

    public static void processControls(ImGuiIO io) {
        io.getKeysDown(keyPressed);
        Arrays.fill(keyPressed, false);
    }
}

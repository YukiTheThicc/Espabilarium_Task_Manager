package frontend;

import backend.Espabilarium;
import backend.api.IEventSystem;
import backend.api.INotifier;
import backend.exceptions.EspRuntimeException;
import backend.utils.EspLogger;
import frontend.events.EventObserver;
import frontend.ui.ImGuiLayer;
import frontend.ui.UserInterface;
import frontend.ui.Window;
import frontend.utils.Resources;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static frontend.events.Event.Type.*;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

/**
 * espbilarium.Espabilarium
 *
 * @author Santiago Barreiro
 */
public class DesktopFront implements ActionListener, MouseListener {

    // ATTRIBUTES
    private final Window window;
    private final Espabilarium espabilarium;
    private final IEventSystem eventSystem;
    private TrayIcon trayIcon;
    private SystemTray tray;
    private ImGuiLayer imgui;
    private boolean windowOpen = false;
    private boolean running = false;
    private boolean debug = false;

    // CONSTRUCTORS
    public DesktopFront() {
        this.window = Window.get();
        this.imgui = null;

        this.espabilarium = new Espabilarium(new INotifier() {
            @Override
            public void notifyUser(String message) {
                if (trayIcon != null) {
                    trayIcon.displayMessage("Espabilarium", message, TrayIcon.MessageType.INFO);
                }
            }

            @Override
            public void warnUser(String message) {

            }
        });
        this.eventSystem = espabilarium.getEventSystem();
    }

    // METHODS
    public void launch(boolean debug) {
        try {
            // Setup tray before Espabilarium to create the tray icon and notifier before teh deamon is started
            setupTray();
            espabilarium.init(null);
            // Create and connect the UIEventObserver to the event system
            eventSystem.attachObserver(new Enum[]{STOW_TASK, USER_EVENT}, new EventObserver(espabilarium.queryMaker()));
            this.debug = debug;
            openWindow();
        } catch (EspRuntimeException e) {
            EspLogger.log(e.getMessage());
            espabilarium.close();
        }
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(this.window.getGlfwWindow());
    }

    private void openWindow() {
        // Init window and imgui layer
        window.init("Espabilarium", "app.png");
        UserInterface ui = new UserInterface(eventSystem, espabilarium.queryMaker(), debug);
        imgui = new ImGuiLayer(window.getGlfwWindow(), ui);
        imgui.init();
        windowOpen = true;

        // Initialize the resource pool and styles
        Resources.init("file");

        // Run window
        run();

        // Close resources
        imgui.destroyImGui();
        window.close();
        windowOpen = false;
    }

    private void setupTray() {
        if (!SystemTray.isSupported()) {
            EspLogger.log("SystemTray is not supported");
            return;
        }
        tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        PopupMenu popup = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(this);
        popup.add(exitItem);
        trayIcon = new TrayIcon(image, "Espabilarium", popup);
        trayIcon.addMouseListener(this);
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new EspRuntimeException(e.getMessage());
        }
    }

    private void run() {
        running = true;
        float bt = (float) glfwGetTime();
        float et;
        float dt = 0f;
        while (running) {
            window.pollEvents();
            imgui.update(dt);
            window.endFrame();
            et = (float) glfwGetTime();
            dt = et - bt;
            bt = et;
            if (shouldClose()) running = false;
        }
    }

    private void close() {
        running = false;
        tray.remove(trayIcon);
        espabilarium.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        close();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() >= 2 && !windowOpen) {
            openWindow();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

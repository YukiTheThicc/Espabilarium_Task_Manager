package frontend;

import backend.Espabilarium;
import backend.api.IEventSystem;
import backend.exceptions.EspRuntimeException;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        this.espabilarium = new Espabilarium();
        this.eventSystem = espabilarium.getEventSystem();
    }

    // METHODS
    public void launch(boolean debug) {
        espabilarium.init(null);
        // Create and connect the UIEventObserver to the event system
        eventSystem.attachObserver(new Enum[]{STOW_TASK, USER_EVENT}, new EventObserver(espabilarium.queryMaker()));
        // Setup tray
        setupTray();
        this.debug = debug;
        openWindow();
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
            System.out.println("SystemTray is not supported");
            return;
        }
        Image image = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\res\\icons\\default.png");
        PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(image, "Espabilarium", popup);
        trayIcon.addMouseListener(this);
        tray = SystemTray.getSystemTray();

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(this);
        popup.add(exitItem);
        trayIcon.setPopupMenu(popup);

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
            eventSystem.dispatchEvents();
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

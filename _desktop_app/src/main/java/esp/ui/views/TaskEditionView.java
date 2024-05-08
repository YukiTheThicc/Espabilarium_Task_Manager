package esp.ui.views;

import esp.api.ITask;
import esp.events.EventSystem;
import esp.ui.AlignX;
import esp.ui.AlignY;
import esp.utils.ImGuiUtils;
import esp.utils.Resources;
import imgui.internal.ImGui;
import imgui.type.ImString;

/**
 * TaskEditionView
 *
 * @author Santiago Barreiro
 */
public class TaskEditionView extends View {

    // ATTRIBUTES
    private ITask task;

    // CONSTRUCTORS
    public TaskEditionView(EventSystem es, ITask task) {
        super(es);
        this.task = task;
    }

    // METHODS
    @Override
    void render() {
        if (ImGui.begin(task.getUuid() + "##" + task.getName())) {

            ImString name = new ImString(task.getName());
            if (ImGuiUtils.inputText(Resources.literal("name"), name)) task.setName(name.get());

            ImGuiUtils.align(AlignX.LEFT, AlignY.BOTTOM, 128f, 24f);
            ImGui.button("save", 60f, 24f);
            ImGui.button("cancel", 60f, 24f);
            ImGui.end();
        }
    }
}

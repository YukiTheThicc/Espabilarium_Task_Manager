package backend.tasks.serializers;

import backend.api.ITaskStowage;
import backend.exceptions.EspRuntimeException;
import backend.tasks.Task;
import com.google.gson.*;
import backend.api.ITask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * TaskSerializer
 *
 * @author Santiago Barreiro
 */

public class TaskSerializerJSON implements ITaskStowage.Serializer {

    // METHODS
    @Override
    public boolean serialize(String dir, ITask task) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(ITask.class, new TaskDeserializer())
                .registerTypeHierarchyAdapter(ITask.Component.class, new ComponentDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        try {
            File directory = new File(dir);
            if (!directory.exists()) directory.mkdir();
            if (directory.isDirectory()) {
                File targetFile = new File(dir + task.getUuid() + ".json");
                targetFile.createNewFile();
                if (targetFile.isFile()) {
                    FileWriter writer = new FileWriter(targetFile.getAbsoluteFile());
                    String a = gson.toJson(task);
                    writer.write(a);
                    writer.close();
                } else {
                    throw new EspRuntimeException("Could not find nor create the dataPath to save data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ITask deserialize(String path) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(ITask.class, new TaskDeserializer())
                .registerTypeAdapter(ITask.Component.class, new ComponentDeserializer())
                .create();
        String inFile;
        ITask loaded = null;
        try {
            inFile = new String(Files.readAllBytes(Paths.get(path)));
            if (!inFile.equals("")) {
                loaded = gson.fromJson(inFile, ITask.class);
            }
        } catch (Exception e) {
            return null;
        }
        return loaded;
    }

    private static class TaskDeserializer implements JsonDeserializer<Task> {

        @Override
        public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement uuid = jsonObject.get("uuid");
            JsonElement name = jsonObject.get("name");
            JsonElement progress = jsonObject.get("progress");
            JsonElement type = jsonObject.get("type");
            JsonElement state = jsonObject.get("state");
            JsonElement priority = jsonObject.get("priority");
            JsonArray children = jsonObject.getAsJsonArray("children");
            Map<String, JsonElement> components = jsonObject.getAsJsonObject("components").asMap();
            try {
                Task task = new Task(uuid.getAsString(), name.getAsString(),
                        Task.Type.valueOf(type.getAsString()), Task.Priority.valueOf(priority.getAsString()));
                task.setProgress(progress.getAsFloat());
                task.setState(Task.State.valueOf(state.getAsString()));
                for (JsonElement c : components.values()) {
                    ITask.Component component = context.deserialize(c, ITask.Component.class);
                    task.addComponent(1, component);
                }
                return task;
            } catch (Exception e) {
                throw new JsonParseException("Failed to load task", e);
            }
        }
    }

    private static class ComponentDeserializer implements JsonDeserializer<ITask.Component>, JsonSerializer<ITask.Component> {

        private final Gson gson;

        public ComponentDeserializer() {
            this.gson = new Gson();
        }

        @Override
        public JsonElement serialize(ITask.Component src, Type typeOfSrc, JsonSerializationContext context) {
            final JsonObject wrapper = new JsonObject();
            wrapper.addProperty("type", src.getClass().getName());
            wrapper.add("components", gson.toJsonTree(src));
            return wrapper;
        }

        @Override
        public ITask.Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("components");
            try {
                return context.deserialize(element, Class.forName(type));
            } catch (Exception e) {
                throw new JsonParseException("Failed to load component: " + type, e);
            }
        }
    }
}

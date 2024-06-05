package backend.tasks;

import backend.api.IComponent;
import backend.api.ITaskStowage;
import backend.exceptions.EspRuntimeException;
import com.google.gson.*;
import backend.api.ITask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TaskSerializer
 *
 * @author Santiago Barreiro
 */

public class TaskSerializer implements ITaskStowage.Serializer {

    // ATTRIBUTES
    private final String dataDir;

    // CONSTRUCTORS
    public TaskSerializer(String path) {
        this.dataDir = path;
    }

    // METHODS
    @Override
    public ITask deserialize(String path) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(ITask.class, new TaskDeserializer())
                .registerTypeAdapter(IComponent.class, new ComponentDeserializer())
                .create();
        String inFile;
        ITask loaded = null;
        try {
            inFile = new String(Files.readAllBytes(Paths.get(path)));
            if (!inFile.equals("")) {
                loaded = gson.fromJson(inFile, Task.class);
            }
        } catch (Exception e) {
            return null;
        }
        return loaded;
    }

    @Override
    public boolean serialize(ITask task) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeHierarchyAdapter(IComponent.class, new ComponentDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        try {
            File directory = new File(dataDir);
            if (!directory.exists()) directory.mkdir();
            if (directory.isDirectory()) {
                File targetFile = new File(dataDir + task.getUuid() + ".json");
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

    private static class TaskDeserializer implements JsonDeserializer<Task> {
        @Override
        public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            try {
                JsonObject components = jsonObject.getAsJsonObject("components");
                Task task = context.deserialize(jsonObject, Task.class);
                return task;
            } catch (Exception e) {
                throw new JsonParseException("Failed to load task", e);
            }
        }
    }

    private static class ComponentDeserializer implements JsonDeserializer<IComponent>, JsonSerializer<IComponent> {

        private final Gson gson ;

        public ComponentDeserializer() {
            this.gson = new Gson();
        }

        @Override
        public JsonElement serialize(IComponent src, Type typeOfSrc, JsonSerializationContext context) {
            final JsonObject wrapper = new JsonObject();
            wrapper.addProperty("type", src.getClass().getName());
            wrapper.add("data", gson.toJsonTree(src));
            return wrapper;
        }

        @Override
        public IComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("data");
            try {
                return context.deserialize(element, Class.forName(type));
            } catch (Exception e) {
                throw new JsonParseException("Failed to load component: " + type, e);
            }
        }
    }
}

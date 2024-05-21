package backend.tasks;

import com.google.gson.*;
import backend.api.ITask;

import java.lang.reflect.Type;

/**
 * TaskSerializer
 *
 * @author Santiago Barreiro
 */

public class TaskSerializer implements JsonDeserializer<ITask> {


    // CONSTRUCTORS
    public TaskSerializer() {
    }

    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        try {
            return context.deserialize(jsonObject, Task.class);
        } catch (Exception e) {
            throw new JsonParseException("Failed to load task", e);
        }
    }
}

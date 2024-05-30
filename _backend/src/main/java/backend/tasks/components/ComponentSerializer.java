package backend.tasks.components;

import backend.api.IComponent;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * ComponentSerializer
 *
 * @author Santiago Barreiro
 */
public class ComponentSerializer implements JsonSerializer<IComponent>, JsonDeserializer<IComponent> {

    // CONSTRUCTORS
    public ComponentSerializer() {
    }

    // METHODS
    @Override
    public JsonElement serialize(IComponent src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }

    @Override
    public IComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName(type));
        } catch (Exception e) {
            throw new JsonParseException("Failed to load component: " + type, e);
        }
    }
}

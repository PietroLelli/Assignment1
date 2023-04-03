package org.example;

import com.google.gson.*;

import java.lang.reflect.Type;

public class HandResultDeserializer implements JsonDeserializer<HandResult> {

    private String getPropertyAsString(JsonObject object, String name) {
        if (object.has(name)) {
            JsonElement value = object.get(name);
            if (value.isJsonNull()) return null;
            return value.getAsString();
        }
        return null;
    }

    private <T> T getPropertyAs(JsonObject object, String name, Class<T> type, JsonDeserializationContext context) {
        if (object.has(name)) {
            JsonElement value = object.get(name);
            if (value.isJsonNull()) return null;
            return context.deserialize(value, type);
        }
        return null;
    }

    @Override
    public HandResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            var object = json.getAsJsonObject();
            var result = object.has("result") ? object.get("result").getAsInt() : null;

            return HandResult.values()[result];
        } catch (ClassCastException e) {
            throw new JsonParseException("Invalid token: " + json, e);
        }
    }
}

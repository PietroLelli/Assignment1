package org.example;

import com.google.gson.*;

import java.lang.reflect.Type;

public class TokenDeserializer implements JsonDeserializer<Token> {

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
    public Token deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if(!json.isJsonObject()){
                return new Token(new User(), "");
            }
            var object = json.getAsJsonObject();

            var balance = object.has("balance") ? object.get("balance").getAsString() : null;
            User user = object.has("user") ? context.deserialize(object.get("user"), User.class) : null;
            return new Token(user, balance);
        } catch (ClassCastException e) {
            throw new JsonParseException("Invalid token: " + json, e);
        }
    }
}

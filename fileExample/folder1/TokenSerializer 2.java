package it.unibo.ds.presentation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class TokenSerializer implements JsonSerializer<Token> {

    @Override
    public JsonElement serialize(Token src, Type typeOfSrc, JsonSerializationContext context) {
        var obj = new JsonObject();
        obj.addProperty("username", src.getUsername());
        obj.add("role", context.serialize(src.getRole()));

        return obj;
    }
}

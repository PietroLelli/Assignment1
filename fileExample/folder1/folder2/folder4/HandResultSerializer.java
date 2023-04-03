package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class HandResultSerializer implements JsonSerializer<HandResult> {

    @Override
    public JsonElement serialize(HandResult src, Type typeOfSrc, JsonSerializationContext context) {
        var object = new JsonObject();
        object.addProperty("result", src.ordinal());
        return object;
    }
}

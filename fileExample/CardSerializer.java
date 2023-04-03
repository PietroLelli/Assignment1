package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CardSerializer implements JsonSerializer<Card> {

    @Override
    public JsonElement serialize(Card src, Type typeOfSrc, JsonSerializationContext context) {
        var object = new JsonObject();
        object.addProperty("suit", src.getSuit().ordinal());
        object.addProperty("value", src.getValue().ordinal());
        return object;
    }
}

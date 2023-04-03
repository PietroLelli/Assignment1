package it.unibo.ds.presentation;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CredentialsSerializer implements JsonSerializer<Credentials> {

    @Override
    public JsonElement serialize(Credentials src, Type typeOfSrc, JsonSerializationContext context) {
        var obj = new JsonObject();

        obj.addProperty("userId", src.getUserId());
        obj.addProperty("password", src.getPassword());

        return obj;

    }
}

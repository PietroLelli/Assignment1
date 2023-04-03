package org.example;

import com.google.gson.*;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        var object = new JsonObject();
        object.addProperty("nickname", src.getNickname());
        object.addProperty("balance", src.getBalance());
        return object;
    }
}

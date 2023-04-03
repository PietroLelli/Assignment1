package it.unibo.ds.presentation;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Locale;

public class RoleSerializer implements JsonSerializer<Role> {
    @Override
    public JsonElement serialize(Role src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.name().toLowerCase(Locale.ROOT));//
    }
}

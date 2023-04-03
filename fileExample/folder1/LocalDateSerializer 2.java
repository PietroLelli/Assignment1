package it.unibo.ds.presentation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class LocalDateSerializer implements JsonSerializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        var obj = new JsonObject();
        obj.addProperty("month", src.getMonthValue());
        obj.addProperty("day", src.getDayOfMonth());
        obj.addProperty("year", src.getYear());

        return obj;
    }
}

package it.unibo.ds.presentation;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Month;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json instanceof JsonObject){

            JsonObject obj = json.getAsJsonObject();
            
            int year = obj.get("year").getAsInt();
            int month = obj.get("month").getAsInt();
            int day = obj.get("day").getAsInt();

            return LocalDate.of(year,month,day);

        }
        throw new JsonParseException("Invalid LocalDate "+json);
    }
}

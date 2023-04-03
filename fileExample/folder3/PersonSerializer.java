import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PersonSerializer implements JsonSerializer<Person> {
    @Override
    public JsonElement serialize(Person src, Type typeOfSrc, JsonSerializationContext context) {
        var obj = new JsonObject();
        obj.addProperty("name", src.getName());
        obj.addProperty("email", src.getEmail());
        obj.addProperty("age", src.getAge());
        return obj;
    }
}

import com.google.gson.*;

import java.lang.reflect.Type;

public class PersonDeserializer implements JsonDeserializer<Person> {
    @Override
    public Person deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json instanceof JsonObject) {
                var object = json.getAsJsonObject();
                if (object.has("name") && object.has("email") && object.has("age")) {
                    var name = object.get("name");
                    var email = object.get("email");
                    var age = object.get("age");
                    return new Person(
                            name.isJsonNull() ? null : name.getAsString(),
                            email.isJsonNull() ? null : email.getAsString(),
                            age.getAsInt()
                    );
                }
            }
            throw new JsonParseException("Invalid person: " + json);
        } catch (ClassCastException e) {
            throw new JsonParseException("Invalid person: " + json, e);
        }
    }
}

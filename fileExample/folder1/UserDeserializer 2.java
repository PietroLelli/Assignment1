package it.unibo.ds.presentation;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserDeserializer implements JsonDeserializer<User> {

    private String getPropertyAsString(JsonObject object, String name) {
        if (object.has(name)) {
            JsonElement value = object.get(name);
            if (value.isJsonNull()) return null;
            return value.getAsString();
        }
        return null;
    }

    private <T> T getPropertyAs(JsonObject object, String name, Class<T> type,  JsonDeserializationContext context) {
        if (object.has(name)) {
            JsonElement value = object.get(name);
            if (value.isJsonNull()) return null;
            return context.deserialize(value, type);
        }
        return null;
    }

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json instanceof JsonObject){
            try{
                JsonObject obj = json.getAsJsonObject();

                String full_name = null;
                if(!obj.get("full_name").isJsonNull()){
                    full_name = obj.get("full_name").getAsString();
                }

                String username = null;
                if(!obj.get("username").isJsonNull()){
                    username = obj.get("username").getAsString();
                }

                String password = null;
                if(!obj.get("password").isJsonNull()){
                    password = obj.get("password").getAsString();
                }

                JsonArray emailsJson = null;
                List<String> emails = new ArrayList<>();
                if(!obj.get("email_addresses").isJsonNull()){
                    emailsJson = obj.get("email_addresses").getAsJsonArray();
                    for(JsonElement email : emailsJson){
                        emails.add(email.getAsString());
                    }
                }

                Role role = null;
                if(!obj.get("role").isJsonNull()){
                    role = Role.valueOf(obj.get("role").getAsString().toUpperCase(Locale.ROOT));
                }

                LocalDate birth_date = null;
                if(!obj.get("role").isJsonNull()){
                    birth_date = context.deserialize(obj.get("birth_date"), LocalDate.class);
                }

                return new User(full_name, username, password, birth_date, role, emails);
            }catch (IllegalStateException e){
                throw new IllegalStateException("Invalid emails: "+json);
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Invalid Role: "+json);
            }
        }
        throw new JsonParseException("Invalid User: "+ json);
    }
}

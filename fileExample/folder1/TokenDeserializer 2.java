package it.unibo.ds.presentation;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Locale;

public class TokenDeserializer implements JsonDeserializer<Token> {
    @Override
    public Token deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json instanceof JsonObject){
            JsonObject obj = json.getAsJsonObject();
            String username = obj.get("username").getAsString();

            try{
                Role role = Role.valueOf(obj.get("role").getAsString().toUpperCase(Locale.ROOT));
                return new Token(username, role);

            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Invalid Role: "+json);
            }

        }

        throw new JsonParseException("Invalid Token: "+json);
    }
}

package it.unibo.ds.presentation;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CredentialsDeserializer implements JsonDeserializer<Credentials> {
    @Override
    public Credentials deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json instanceof JsonObject){
            JsonObject obj = json.getAsJsonObject();

            String userId = null;
            if(!obj.get("userId").isJsonNull()){
                userId = obj.get("userId").getAsString();
            }

            String password = null;
            if(!obj.get("password").isJsonNull()){
                password = obj.get("password").getAsString();
            }

            return new Credentials(userId,password);


        }
        throw new JsonParseException("Invalid token "+json);
    }
}

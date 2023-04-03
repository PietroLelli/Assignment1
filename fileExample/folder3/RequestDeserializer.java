package it.unibo.ds.presentation;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RequestDeserializer implements JsonDeserializer<Request<?>> {
    @Override
    public Request<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json instanceof JsonObject){
            JsonObject obj = json.getAsJsonObject();

            String method = obj.get("method").getAsString();
            switch (method){
                case "authorize":

                    Credentials credentials = context.deserialize(obj.get("argument"), Credentials.class);
                    return new Request<>(method, credentials);
                case "register":

                    User user = context.deserialize(obj.get("argument"), User.class);
                    return new Request<>(method, user);
                default:

                    throw new JsonParseException("Invalid Request argument: "+json);
            }
        }
        throw new JsonParseException("Invalid Request: "+ json);
    }
}

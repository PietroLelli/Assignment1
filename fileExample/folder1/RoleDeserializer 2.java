package it.unibo.ds.presentation;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Locale;

public class RoleDeserializer implements JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json instanceof JsonPrimitive){
            JsonPrimitive primitive = json.getAsJsonPrimitive();

            if(primitive.isString()){

                String primitiveAsString = primitive.getAsString();
                try{
                    return Role.valueOf(primitiveAsString.toUpperCase(Locale.ROOT));

                }catch (IllegalArgumentException e){

                    throw new JsonParseException("Invalid Role: "+json);
                }
            }
        }
        throw new JsonParseException("Invalid Role: "+json);
    }
}

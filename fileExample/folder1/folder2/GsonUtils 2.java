package it.unibo.ds.presentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class GsonUtils {
    public static Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .registerTypeAdapter(Request.class,new RequestDeserializer())//per ogni classe da serializzare bisogna aggiungere il deserializzatore
                .registerTypeAdapter(User.class,new UserSerializer())
                .registerTypeAdapter(User.class,new UserDeserializer())
                .registerTypeAdapter(Token.class,new TokenDeserializer())
                .registerTypeAdapter(Token.class,new TokenSerializer())
                .registerTypeAdapter(Role.class,new RoleDeserializer())
                .registerTypeAdapter(Role.class,new RoleSerializer())
                .registerTypeAdapter(LocalDate.class,new LocalDateDeserializer())
                .registerTypeAdapter(LocalDate.class,new LocalDateSerializer())
                .registerTypeAdapter(Credentials.class,new CredentialsSerializer())
                .registerTypeAdapter(Credentials.class,new CredentialsDeserializer())
                .create();
    }
}

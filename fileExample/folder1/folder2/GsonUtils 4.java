package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class GsonUtils {
    public static Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(User.class, new UserDeserializer())
                .registerTypeAdapter(Token.class, new TokenSerializer())
                .registerTypeAdapter(Token.class, new TokenDeserializer())
                .registerTypeAdapter(Card.class, new CardSerializer())
                .registerTypeAdapter(Card.class, new CardDeserializer())
                .registerTypeAdapter(HandResult.class, new HandResultSerializer())
                .registerTypeAdapter(HandResult.class, new HandResultDeserializer())
                .create();
    }
}

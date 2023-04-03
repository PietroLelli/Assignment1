package org.example;

public class AbstractApi {
    private final Blackjack storage;

    public AbstractApi(Blackjack storage) {
        this.storage = storage;
    }

    public Blackjack storage() {
        return storage;
    }
}

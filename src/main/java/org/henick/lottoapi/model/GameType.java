package org.henick.lottoapi.model;

import org.henick.lottoapi.exception.UnknownGameException;

import java.util.Arrays;

public enum GameType {
    LOTTO("Lotto"),
    LOTTOPLUS("LottoPlus"),
    MINILOTTO("MiniLotto"),
    EUROJACKPOT("EuroJackpot");

    private final String apiValue;

    GameType(String apiValue) {
        this.apiValue = apiValue;
    }

    public String getApiValue() {
        return apiValue;
    }

    public static GameType from(String value) {
        return Arrays.stream(values())
                .filter(gameType -> gameType.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new UnknownGameException("Unknown game type: " + value));
    }

    public static boolean contains(String value) {
        return Arrays.stream(values())
                .anyMatch(gameType -> gameType.getApiValue().equalsIgnoreCase(value));
    }

}

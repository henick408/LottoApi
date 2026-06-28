package org.henick.lottoapi.model;

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
                .orElseThrow(() -> new IllegalArgumentException("Unknown game type: " + value));
    }

}

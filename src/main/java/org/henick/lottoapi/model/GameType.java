package org.henick.lottoapi.model;

import org.henick.lottoapi.exception.UnknownGameException;

import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

public enum GameType {
    LOTTO("Lotto", List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY), OffsetTime.of(20, 20, 0, 0, ZoneOffset.UTC)),
    LOTTOPLUS("LottoPlus", LOTTO.drawWeekDays, LOTTO.drawTime),
    MINILOTTO("MiniLotto", List.of(DayOfWeek.values()), LOTTO.drawTime),
    EUROJACKPOT("EuroJackpot", List.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY), OffsetTime.of(18, 15, 0, 0, ZoneOffset.UTC));

    private final String apiValue;
    private final List<DayOfWeek> drawWeekDays;
    private final OffsetTime drawTime;

    GameType(String apiValue, List<DayOfWeek> drawWeekDays, OffsetTime drawTime) {
        if (drawWeekDays.isEmpty()) {
            throw new IllegalArgumentException("drawWeekDays must not be empty for " + apiValue);
        }
        this.apiValue = apiValue;
        this.drawWeekDays = drawWeekDays;
        this.drawTime = drawTime;
    }

    public String getApiValue() {
        return apiValue;
    }

    public List<DayOfWeek> getDrawWeekDays() {
        return drawWeekDays;
    }

    public OffsetTime getDrawTime() {
        return drawTime;
    }

    public static GameType from(String value) {
        return Arrays.stream(values())
                .filter(gameType -> gameType.apiValue.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new UnknownGameException("Unknown game type: " + value));
    }

    public static boolean contains(String value) {
        return Arrays.stream(values()).anyMatch(gameType -> gameType.apiValue.equalsIgnoreCase(value));
    }
}
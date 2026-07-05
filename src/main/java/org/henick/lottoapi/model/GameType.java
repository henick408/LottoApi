package org.henick.lottoapi.model;

import org.henick.lottoapi.exception.UnknownGameException;

import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum GameType {
    LOTTO("Lotto", scheduleOf(
            OffsetTime.of(20, 0, 0, 0, ZoneOffset.UTC),
            DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)),
    LOTTOPLUS("LottoPlus", LOTTO.drawSchedule),
    MINILOTTO("MiniLotto", scheduleOf(
            OffsetTime.of(20, 0, 0, 0, ZoneOffset.UTC),
            DayOfWeek.values())),
    EUROJACKPOT("EuroJackpot", Map.of(
            DayOfWeek.TUESDAY, OffsetTime.of(18, 0, 0, 0, ZoneOffset.UTC),
            DayOfWeek.FRIDAY, OffsetTime.of(18, 15, 0, 0, ZoneOffset.UTC)));

    private final String apiValue;
    private final Map<DayOfWeek, OffsetTime> drawSchedule;

    GameType(String apiValue, Map<DayOfWeek, OffsetTime> drawSchedule) {
        if (drawSchedule.isEmpty()) {
            throw new IllegalArgumentException("drawSchedule must not be empty for " + apiValue);
        }
        this.apiValue = apiValue;
        this.drawSchedule = drawSchedule;
    }

    // helper: same draw time on several days
    private static Map<DayOfWeek, OffsetTime> scheduleOf(OffsetTime time, DayOfWeek... days) {
        return Arrays.stream(days)
                .collect(Collectors.toUnmodifiableMap(Function.identity(), d -> time));
    }

    public String getApiValue() {
        return apiValue;
    }

    public static Set<GameType> getGameTypesByDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .filter(gameType -> gameType.drawSchedule.containsKey(dayOfWeek))
                .collect(Collectors.toSet());
    }

    public Set<DayOfWeek> getDrawWeekDays() {
        return drawSchedule.keySet();
    }

    // returns the scheduled draw time for a given day, or null if it's not a draw day for this game
    public OffsetTime getDrawTime(DayOfWeek day) {
        return drawSchedule.get(day);
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
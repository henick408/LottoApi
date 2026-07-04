package org.henick.lottoapi.util;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

@Component
public class DrawDateProvider {

    public LocalDate getLastDrawDate(GameType gameType) {
        ZoneOffset drawOffset = gameType.getDrawTime().getOffset();
        return getLastDrawDate(gameType, OffsetDateTime.now(drawOffset));
    }

    // package-private overload so tests can inject a fixed "now"
    LocalDate getLastDrawDate(GameType gameType, OffsetDateTime now) {
        ZoneOffset drawOffset = gameType.getDrawTime().getOffset();
        OffsetDateTime nowAtDrawOffset = now.withOffsetSameInstant(drawOffset);
        LocalDate today = nowAtDrawOffset.toLocalDate();

        boolean todayDrawWindowPassed = gameType.getDrawWeekDays().contains(today.getDayOfWeek())
                && !nowAtDrawOffset.toOffsetTime().isBefore(gameType.getDrawTime());

        LocalDate searchFrom = todayDrawWindowPassed ? today : today.minusDays(1);

        return gameType.getDrawWeekDays().stream()
                .map(day -> searchFrom.with(TemporalAdjusters.previousOrSame(day)))
                .max(LocalDate::compareTo)
                .orElseThrow(); // guarded by the non-empty drawWeekDays check in GameType's constructor
    }

    public boolean isLastDrawDate(GameType gameType, OffsetDateTime dateTime) {
        return dateTime.toLocalDate().equals(getLastDrawDate(gameType));
    }

    public boolean isLatestDraw(Draw draw) {
        return draw.getDrawDate().toLocalDate().equals(getLastDrawDate(draw.getGameType()));
    }
}
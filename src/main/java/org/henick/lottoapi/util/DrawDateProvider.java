package org.henick.lottoapi.util;

import org.henick.lottoapi.model.GameType;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

@Component
public class DrawDateProvider {

    public OffsetDateTime getLastDrawDate(GameType gameType) {
        ZoneOffset drawOffset = gameType.getDrawTime().getOffset();
        return getLastDrawDate(gameType, OffsetDateTime.now(drawOffset));
    }

    OffsetDateTime getLastDrawDate(GameType gameType, OffsetDateTime now) {
        ZoneOffset drawOffset = gameType.getDrawTime().getOffset();
        OffsetDateTime nowAtDrawOffset = now.withOffsetSameInstant(drawOffset);

        OffsetDateTime todaysDraw = nowAtDrawOffset.with(gameType.getDrawTime().toLocalTime());

        boolean todayDrawHappened = gameType.getDrawWeekDays().contains(nowAtDrawOffset.getDayOfWeek())
                && !nowAtDrawOffset.isBefore(todaysDraw);

        OffsetDateTime searchFrom = todayDrawHappened ? nowAtDrawOffset : nowAtDrawOffset.minusDays(1);

        return gameType.getDrawWeekDays().stream()
                .map(day -> searchFrom.with(TemporalAdjusters.previousOrSame(day))
                        .with(gameType.getDrawTime().toLocalTime()))
                .max(OffsetDateTime::compareTo)
                .orElseThrow(); // guarded by the non-empty drawWeekDays check in GameType's constructor
    }
}
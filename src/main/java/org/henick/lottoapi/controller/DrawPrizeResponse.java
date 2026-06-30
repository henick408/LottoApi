package org.henick.lottoapi.controller;

import org.henick.lottoapi.model.GameType;

import java.time.OffsetDateTime;
import java.util.List;

public record DrawPrizeResponse(
        long drawSystemId,
        GameType gameType,
        OffsetDateTime drawDate,
        List<PrizeDto> prizes
) {
}

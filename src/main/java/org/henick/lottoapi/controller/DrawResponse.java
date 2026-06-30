package org.henick.lottoapi.controller;

import org.henick.lottoapi.model.GameType;

import java.time.OffsetDateTime;
import java.util.List;

public record DrawResponse(
        long drawSystemId,
        OffsetDateTime drawDate,
        GameType gameType,
        List<Integer> results,
        List<Integer> specialResults
) {
}

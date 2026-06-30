package org.henick.lottoapi.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.henick.lottoapi.model.GameType;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record DrawResponse(
        long drawSystemId,
        OffsetDateTime drawDate,
        GameType gameType,
        List<Integer> results,
        List<Integer> specialResults
) {
}

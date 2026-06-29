package org.henick.lottoapi.client;

import java.time.OffsetDateTime;
import java.util.List;

record GameResultDto(
        OffsetDateTime drawDate,
        long drawSystemId,
        String gameType,
        List<Integer> resultsJson,
        List<Integer> specialResults
) {
}

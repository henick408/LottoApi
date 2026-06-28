package org.henick.lottoapi.client;

import java.time.OffsetDateTime;
import java.util.List;

record DrawDto(
        long drawSystemId,
        OffsetDateTime drawDate,
        String gameType,
        Integer multiplierValue,
        List<GameResultDto> results,
        boolean showSpecialResults,
        boolean isNewEuroJackpotDraw
) {
}

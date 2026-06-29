package org.henick.lottoapi.client;

import java.time.OffsetDateTime;
import java.util.Map;

record DrawPrizeDto(
        Map<Integer, PrizeDto> prizes,
        OffsetDateTime drawDate,
        Long drawSystemId,
        String gameType,
        boolean prizesEmpty
) {
}

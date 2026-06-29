package org.henick.lottoapi.client;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.springframework.stereotype.Component;

@Component
class LottoMapper {

    Draw fromDto(DrawDto dto) {
        GameResultDto result = dto.results().stream()
                .filter(resultDto -> resultDto.gameType().equals(dto.gameType()))
                .findFirst().get();
        return new Draw(
                result.drawSystemId(),
                result.drawDate(),
                GameType.from(result.gameType()),
                result.resultsJson(),
                result.specialResults()
        );
    }

}

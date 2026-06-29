package org.henick.lottoapi.client;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.springframework.stereotype.Component;

@Component
class LottoMapper {

    Draw fromDrawDto(DrawDto dto) {
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

    Draw fromGameResultDto(GameResultDto dto) {
        return new Draw(
                dto.drawSystemId(),
                dto.drawDate(),
                GameType.from(dto.gameType()),
                dto.resultsJson(),
                dto.specialResults()
        );
    }

}

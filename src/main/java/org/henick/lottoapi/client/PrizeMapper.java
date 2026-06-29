package org.henick.lottoapi.client;

import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;
import org.henick.lottoapi.model.Prize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrizeMapper {

    DrawPrize fromDto(DrawPrizeDto dto) {
        List<Prize> prizes = dto.prizes().entrySet().stream()
                .map(entry -> new Prize(
                        entry.getKey(),
                        entry.getValue().prize(),
                        entry.getValue().prizeValue()
                ))
                .toList();
        return new DrawPrize(GameType.from(dto.gameType()), dto.drawDate(), prizes);
    }

}

package org.henick.lottoapi.controller;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.DrawPrize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseMapper {

    DrawResponse toResponse(Draw draw) {
        return new DrawResponse(
                draw.getId(),
                draw.getDrawDate(),
                draw.getGameType(),
                draw.getResults(),
                draw.getSpecialResults()
        );
    }

    DrawPrizeResponse toResponse(DrawPrize drawPrize) {
        List<PrizeDto> prizes = drawPrize.getPrizes().stream()
                .map(prize -> new PrizeDto(
                        prize.getDegree(),
                        prize.getWinnersCount(),
                        prize.getValue()
                ))
                .toList();

        return new DrawPrizeResponse(
                drawPrize.getId(),
                drawPrize.getGameType(),
                drawPrize.getDrawDate(),
                prizes
        );
    }

}

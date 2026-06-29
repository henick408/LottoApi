package org.henick.lottoapi.model;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
public class DrawPrize {
    GameType gameType;
    OffsetDateTime drawDate;
    List<Prize> prizes;

    public DrawPrize(GameType gameType, OffsetDateTime drawDate, List<Prize> prizes) {
        this.gameType = gameType;
        this.drawDate = drawDate;
        this.prizes = prizes;
    }
}

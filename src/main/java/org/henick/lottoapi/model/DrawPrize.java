package org.henick.lottoapi.model;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
public class DrawPrize {
    long id;
    GameType gameType;
    OffsetDateTime drawDate;
    List<Prize> prizes;

    public DrawPrize(long id, GameType gameType, OffsetDateTime drawDate, List<Prize> prizes) {
        this.id = id;
        this.gameType = gameType;
        this.drawDate = drawDate;
        this.prizes = prizes;
    }
}

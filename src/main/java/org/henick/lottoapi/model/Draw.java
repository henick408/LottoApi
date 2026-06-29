package org.henick.lottoapi.model;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
public class Draw {

    private final long id;
    private final OffsetDateTime drawDate;
    private final GameType gameType;
    List<Integer> results;
    List<Integer> specialResults;

    public Draw(long id, OffsetDateTime drawDate, GameType gameType, List<Integer> results, List<Integer> specialResults) {
        this.id = id;
        this.drawDate = drawDate;
        this.gameType = gameType;
        this.results = results;
        this.specialResults = specialResults;
    }

}

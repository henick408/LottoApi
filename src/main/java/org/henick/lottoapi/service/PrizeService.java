package org.henick.lottoapi.service;

import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;

import java.time.LocalDate;

public interface PrizeService {

    DrawPrize getPrize(GameType gameType, long drawSystemId);
    DrawPrize getPrizeForLastGame(GameType gameType);
    DrawPrize getPrize(GameType gameType, LocalDate drawDate);

}

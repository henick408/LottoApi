package org.henick.lottoapi.service;

import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;

public interface PrizeService {

    DrawPrize getPrize(GameType gameType, long drawSystemId);

}

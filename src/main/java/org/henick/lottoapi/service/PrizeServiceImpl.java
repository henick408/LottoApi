package org.henick.lottoapi.service;

import org.henick.lottoapi.client.LottoApiClient;
import org.henick.lottoapi.exception.DrawNotFoundByGameTypeAndId;
import org.henick.lottoapi.exception.DrawNotFoundException;
import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;
import org.springframework.stereotype.Service;

@Service
public class PrizeServiceImpl implements PrizeService {

    private final LottoApiClient lottoApiClient;

    public PrizeServiceImpl(LottoApiClient lottoApiClient) {
        this.lottoApiClient = lottoApiClient;
    }

    @Override
    public DrawPrize getPrize(GameType gameType, long drawSystemId) {
        return lottoApiClient.getPrize(gameType.getApiValue(), drawSystemId)
                .orElseThrow(() -> new DrawNotFoundByGameTypeAndId(gameType, drawSystemId));
    }

    @Override
    public DrawPrize getPrizeForLastGame(GameType gameType) {
        long drawSystemId = lottoApiClient.getLastResultsByGame(gameType.getApiValue())
                .orElseThrow(() -> new DrawNotFoundException("No draws returned from API for game: " + gameType.getApiValue()))
                .getId();
        return lottoApiClient.getPrize(gameType.getApiValue(), drawSystemId)
                .orElseThrow(() -> new DrawNotFoundByGameTypeAndId(gameType, drawSystemId));
    }

}

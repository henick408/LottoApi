package org.henick.lottoapi.service;

import org.henick.lottoapi.client.LottoApiClient;
import org.henick.lottoapi.exception.DrawNotFoundException;
import org.henick.lottoapi.exception.DrawNotFoundByDateException;
import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    private final LottoApiClient lottoApiClient;

    public ResultServiceImpl(LottoApiClient lottoApiClient) {
        this.lottoApiClient = lottoApiClient;
    }

    @Override
    public Draw getLastResults(GameType gameType) {
        return lottoApiClient.getLastResultsByGame(gameType.getApiValue())
                .orElseThrow(() -> new DrawNotFoundException("No draws returned from API for game: " + gameType));
    }

    @Override
    public List<Draw> getLastResults() {
        return lottoApiClient.getLastResults();
    }

    @Override
    public List<Draw> getResultsByDate(LocalDate drawDate) {
        return lottoApiClient.getResultsByDate(drawDate);
    }

    @Override
    public Draw getResultsByDate(LocalDate drawDate, GameType gameType) {
        return lottoApiClient.getResultsByDateByGame(drawDate, gameType.getApiValue())
                .orElseThrow(() -> new DrawNotFoundByDateException(gameType, drawDate));
    }

}

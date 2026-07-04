package org.henick.lottoapi.service;

import org.henick.lottoapi.client.LottoApiClient;
import org.henick.lottoapi.exception.DrawNotFoundException;
import org.henick.lottoapi.exception.DrawNotFoundByDateException;
import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.henick.lottoapi.repository.DrawRepository;
import org.henick.lottoapi.util.DrawDateProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    private final LottoApiClient lottoApiClient;
    private final DrawDateProvider drawDateProvider;
    private final DrawRepository drawRepository;

    public ResultServiceImpl(LottoApiClient lottoApiClient, DrawDateProvider drawDateProvider, DrawRepository drawRepository) {
        this.lottoApiClient = lottoApiClient;
        this.drawDateProvider = drawDateProvider;
        this.drawRepository = drawRepository;
    }

    @Override
    public Draw getLastResults(GameType gameType) {
        Draw latestDrawFromDatabase = drawRepository.findTopByGameTypeOrderByDrawDateDesc(gameType)
                .orElse(null);
        if (latestDrawFromDatabase != null && drawDateProvider.isLatestDraw(latestDrawFromDatabase)) {
            return latestDrawFromDatabase;
        }
        Draw drawFromClient = lottoApiClient.getLastResultsByGame(gameType.getApiValue())
                .orElseThrow(() -> new DrawNotFoundException("No draws returned from API for game: " + gameType));
        drawRepository.save(drawFromClient);
        return drawFromClient;
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

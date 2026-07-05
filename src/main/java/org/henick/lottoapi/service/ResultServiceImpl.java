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
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        upsert(drawFromClient);
        return drawFromClient;
    }

    @Override
    public List<Draw> getLastResults() {
        List<Draw> latestResultsFromDatabasePerGame = Arrays.stream(GameType.values())
                .map(drawRepository::findTopByGameTypeOrderByDrawDateDesc)
                .flatMap(Optional::stream)
                .toList();

        boolean isAllLatestDraws = latestResultsFromDatabasePerGame.stream()
                .allMatch(draw -> drawDateProvider.isLastDrawDate(draw.getGameType(), draw.getDrawDate()));

        boolean containsAllGameTypes = latestResultsFromDatabasePerGame.stream()
                .map(Draw::getGameType)
                .collect(Collectors.toSet())
                .containsAll(Set.of(GameType.values()));

        if (isAllLatestDraws && containsAllGameTypes) {
            return latestResultsFromDatabasePerGame;
        }

        return lottoApiClient.getLastResults().stream()
                .map(this::upsert)
                .toList();

    }

    private Draw upsert(Draw drawFromClient) {
        return drawRepository.findByGameTypeAndDrawDate(drawFromClient.getGameType(), drawFromClient.getDrawDate())
                .map(existing -> {
                    existing.setResults(drawFromClient.getResults());
                    existing.setSpecialResults(drawFromClient.getSpecialResults());
                    return existing;
                })
                .orElseGet(() -> drawRepository.save(drawFromClient));
    }

    @Override
    public List<Draw> getResultsByDate(LocalDate drawDate) {
        return lottoApiClient.getResultsByDate(drawDate);
    }

    @Override
    public Draw getResultsByDate(LocalDate drawDate, GameType gameType) {
        OffsetTime drawTime = gameType.getDrawTime(drawDate.getDayOfWeek());
        Draw drawFromDatabase = drawRepository.findByGameTypeAndDrawDate(gameType, drawDate.atTime(drawTime))
                .orElse(null);

        if (drawFromDatabase != null) {
            return drawFromDatabase;
        }

        Draw drawFromClient = lottoApiClient.getResultsByDateByGame(drawDate, gameType.getApiValue())
                .orElseThrow(() -> new DrawNotFoundByDateException(gameType, drawDate));

        upsert(drawFromClient);
        return drawFromClient;

    }

}

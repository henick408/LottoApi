package org.henick.lottoapi.client;

import org.henick.lottoapi.exception.DrawNotFoundException;
import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class LottoApiClientImpl implements LottoApiClient {

    private final RestClient lottoClient;
    private final DrawMapper drawMapper;
    private final PrizeMapper prizeMapper;

    LottoApiClientImpl(RestClient lottoClient, DrawMapper drawMapper, PrizeMapper prizeMapper) {
        this.lottoClient = lottoClient;
        this.drawMapper = drawMapper;
        this.prizeMapper = prizeMapper;
    }

    @Override
    public Optional<Draw> getLastResultsByGame(String gameTypeRaw) {
        GameType gameType = GameType.from(gameTypeRaw);
        String apiValue = gameType.getApiValue();

        List<DrawDto> draws = lottoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/draw-results/last-results-per-game")
                        .queryParam("gameType", apiValue)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (draws == null || draws.isEmpty()) {
            throw new DrawNotFoundException("No draws returned from API for game: " + apiValue);
        }

        return draws.stream()
                .filter(dto -> apiValue.equals(dto.gameType()))
                .findFirst()
                .map(drawMapper::fromDrawDto);
    }

    @Override
    public List<Draw> getLastResults() {

        List<DrawDto> draws = lottoClient.get()
                .uri("/draw-results/last-results")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return draws.stream()
                .filter(drawDto -> GameType.contains(drawDto.gameType()))
                .flatMap(drawDto -> drawDto.results().stream())
                .map(drawMapper::fromGameResultDto)
                .toList();

    }

    @Override
    public List<Draw> getResultsByDate(LocalDate drawDate) {

        List<DrawDto> draws = lottoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/draw-results/by-date")
                        .queryParam("drawDate", drawDate)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return draws.stream()
                .filter(drawDto -> GameType.contains(drawDto.gameType()))
                .flatMap(drawDto -> drawDto.results().stream())
                .map(drawMapper::fromGameResultDto)
                .toList();

    }

    @Override
    public Optional<DrawPrize> getPrize(String gameTypeRaw, long drawSystemId) {
        GameType gameType = GameType.from(gameTypeRaw);
        String apiValue = gameType.getApiValue();

        List<DrawPrizeDto> prizes = lottoClient.get()
                .uri("/draw-prizes/{drawType}/{drawSystemId}", apiValue, drawSystemId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return prizes.stream()
                .filter(dto -> apiValue.equals(dto.gameType()))
                .map(prizeMapper::fromDto)
                .findFirst();
    }

    @Override
    public Optional<Draw> getResultsByDateByGame(LocalDate drawDate, String gameTypeRaw) {
        GameType gameType = GameType.from(gameTypeRaw);
        String apiValue = gameType.getApiValue();

        List<DrawDto> draws = lottoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/draw-results/by-date")
                        .queryParam("drawDate", drawDate)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return draws.stream()
                .filter(dto -> apiValue.equals(dto.gameType()))
                .findFirst()
                .map(drawMapper::fromDrawDto);

    }


}

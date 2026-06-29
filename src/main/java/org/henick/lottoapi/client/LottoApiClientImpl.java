package org.henick.lottoapi.client;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class LottoApiClientImpl implements LottoApiClient {

    private final RestClient lottoClient;
    private final LottoMapper lottoMapper;

    LottoApiClientImpl(RestClient lottoClient, LottoMapper lottoMapper) {
        this.lottoClient = lottoClient;
        this.lottoMapper = lottoMapper;
    }

    @Override
    public Draw getLastResultsByGame(String gameTypeRaw) {
        GameType gameType = GameType.from(gameTypeRaw);
        String apiValue = gameType.getApiValue();

        List<DrawDto> draws = lottoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/draw-results/last-results-per-game")
                        .queryParam("gameType", apiValue)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (draws == null || draws.isEmpty()) {
            throw new IllegalStateException("No draws returned from API for game: " + apiValue);
        }

        return draws.stream()
                .filter(dto -> apiValue.equals(dto.gameType()))
                .findFirst()
                .map(lottoMapper::fromDto)
                .orElseThrow(() ->
                        new IllegalStateException("No matching draw for game: " + apiValue));
    }

}

package org.henick.lottoapi.service;

import org.henick.lottoapi.client.LottoApiClient;
import org.henick.lottoapi.exception.DrawNotFoundByGameTypeAndId;
import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;
import org.henick.lottoapi.repository.DrawPrizeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrizeServiceImpl implements PrizeService {

    private final LottoApiClient lottoApiClient;
    private final DrawPrizeRepository drawPrizeRepository;
    private final ResultService resultService;

    public PrizeServiceImpl(LottoApiClient lottoApiClient, DrawPrizeRepository drawPrizeRepository, ResultService resultService) {
        this.lottoApiClient = lottoApiClient;
        this.drawPrizeRepository = drawPrizeRepository;
        this.resultService = resultService;
    }

    @Override
    public DrawPrize getPrize(GameType gameType, long drawSystemId) {

        DrawPrize drawPrizeFromDatabase = drawPrizeRepository.findByGameTypeAndDrawSystemId(gameType, drawSystemId)
                .orElse(null);

        if (drawPrizeFromDatabase != null) {
            return drawPrizeFromDatabase;
        }


        DrawPrize drawPrizeFromClient = lottoApiClient.getPrize(gameType.getApiValue(), drawSystemId)
                .orElseThrow(() -> new DrawNotFoundByGameTypeAndId(gameType, drawSystemId));

        return upsert(drawPrizeFromClient);

    }

    private DrawPrize upsert(DrawPrize drawPrize) {
        return drawPrizeRepository.findByGameTypeAndDrawSystemId(drawPrize.getGameType(), drawPrize.getDrawSystemId())
                .map(existing -> {
                    existing.setPrizes(drawPrize.getPrizes());
                    return existing;
                })
                .orElseGet(() -> drawPrizeRepository.save(drawPrize));
    }

    @Override
    public DrawPrize getPrizeForLastGame(GameType gameType) {
        long drawSystemId = resultService.getLastResults(gameType).getDrawSystemId();

        return getPrize(gameType, drawSystemId);
    }

    @Override
    public DrawPrize getPrize(GameType gameType, LocalDate drawDate) {
        long drawSystemId = resultService.getResultsByDate(drawDate, gameType).getDrawSystemId();

        return getPrize(gameType, drawSystemId);
    }

}

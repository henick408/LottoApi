package org.henick.lottoapi.client;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.DrawPrize;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LottoApiClient {

    Optional<Draw> getLastResultsByGame(String gameTypeRaw);
    List<Draw> getLastResults();

    Optional<Draw> getResultsByDateByGame(LocalDate drawDate, String gameTypeRaw);
    List<Draw> getResultsByDate(LocalDate drawDate);

    Optional<DrawPrize> getPrize(String gameTypeRaw, long drawSystemId);

}

package org.henick.lottoapi.client;

import org.henick.lottoapi.model.Draw;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LottoApiClient {

    Optional<Draw> getLastResultsByGame(String gameTypeRaw);
    List<Draw> getLastResults();

    List<Draw> getResultsByDate(LocalDate drawDate);
    Optional<Draw> getResultsByDateByGame(LocalDate drawDate, String gameTypeRaw);

}

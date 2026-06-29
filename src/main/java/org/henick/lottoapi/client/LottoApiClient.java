package org.henick.lottoapi.client;

import org.henick.lottoapi.model.Draw;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface LottoApiClient {

    Draw getLastResultsByGame(String gameTypeRaw);
    List<Draw> getLastResults();

    List<Draw> getResultsByDate(LocalDate drawDate);

}

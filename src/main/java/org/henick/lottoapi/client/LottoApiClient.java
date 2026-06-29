package org.henick.lottoapi.client;

import org.henick.lottoapi.model.Draw;

import java.util.List;

public interface LottoApiClient {

    Draw getLastResultsByGame(String gameTypeRaw);
    List<Draw> getLastResults();

}

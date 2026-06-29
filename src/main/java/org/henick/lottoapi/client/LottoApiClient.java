package org.henick.lottoapi.client;

import org.henick.lottoapi.model.Draw;

public interface LottoApiClient {

    Draw getLastResultsByGame(String gameTypeRaw);


}

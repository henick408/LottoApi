package org.henick.lottoapi.exception;

import org.henick.lottoapi.model.GameType;

public class DrawNotFoundByGameTypeAndId extends RuntimeException {
    public DrawNotFoundByGameTypeAndId(GameType gameType, long drawSystemId) {
        super("No draw for game: '" + gameType.getApiValue() + "' with drawSystemId: '" + drawSystemId + "'");
    }
}

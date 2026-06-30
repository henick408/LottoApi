package org.henick.lottoapi.exception;

import org.henick.lottoapi.model.GameType;

import java.time.LocalDate;

public class DrawNotFoundByDateException extends RuntimeException {
    public DrawNotFoundByDateException(GameType gameType, LocalDate drawDate) {
        super("No draw for game: '" + gameType.getApiValue() + "' on day: '" + drawDate + "'");
    }
}

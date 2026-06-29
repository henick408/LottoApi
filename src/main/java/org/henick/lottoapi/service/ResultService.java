package org.henick.lottoapi.service;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;

import java.time.LocalDate;
import java.util.List;

public interface ResultService {

    Draw getLastResults(GameType gameType);
    List<Draw> getLastResults();

    List<Draw> getResultsByDate(LocalDate drawDate);
    Draw getResultsByDate(LocalDate drawDate, GameType gameType);

}

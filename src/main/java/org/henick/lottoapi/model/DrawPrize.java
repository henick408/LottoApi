package org.henick.lottoapi.model;

import java.time.LocalDate;
import java.util.List;

public class DrawPrize {
    GameType gameType;
    LocalDate drawDate;
    List<Prize> prizes;
}

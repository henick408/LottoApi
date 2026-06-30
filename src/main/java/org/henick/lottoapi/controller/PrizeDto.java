package org.henick.lottoapi.controller;

public record PrizeDto(
        int degree,
        int winnerCount,
        double value
) {
}

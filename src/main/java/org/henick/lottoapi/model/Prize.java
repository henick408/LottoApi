package org.henick.lottoapi.model;

import lombok.Getter;

@Getter
public class Prize {
    int degree;
    long winnersCount;
    double value;

    public Prize(int degree, long winnersCount, double value) {
        this.degree = degree;
        this.winnersCount = winnersCount;
        this.value = value;
    }

}

package org.henick.lottoapi.model;

import lombok.Getter;

@Getter
public class Prize {
    int degree;
    int winnersCount;
    double value;

    public Prize(int degree, int winnersCount, double value) {
        this.degree = degree;
        this.winnersCount = winnersCount;
        this.value = value;
    }

}

package org.henick.lottoapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "degree", nullable = false)
    private int degree;

    @Column(name = "winners_count", nullable = false)
    private int winnersCount;

    @Column(name = "value", nullable = false)
    private double value;

    @ManyToOne
    @JoinColumn(name = "draw_prize_id", nullable = false)
    private DrawPrize drawPrize;

    public Prize(int degree, int winnersCount, double value) {
        this.degree = degree;
        this.winnersCount = winnersCount;
        this.value = value;
    }

    public Prize() {}

}

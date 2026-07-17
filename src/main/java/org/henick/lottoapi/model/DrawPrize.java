package org.henick.lottoapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DrawPrize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "draw_system_id")
    private Long drawSystemId;

    @Column(name = "game_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @Column(name = "draw_date", nullable = false)
    private OffsetDateTime drawDate;

    @OneToMany(
            mappedBy = "drawPrize",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @OrderBy("degree ASC")
    private List<Prize> prizes = new ArrayList<>();

    public DrawPrize(Long drawSystemId, GameType gameType, OffsetDateTime drawDate, List<Prize> prizes) {
        this.drawSystemId = drawSystemId;
        this.gameType = gameType;
        this.drawDate = drawDate;
        this.prizes = prizes;
        prizes.forEach(prize -> prize.setDrawPrize(this));
    }

    public DrawPrize() {}

}

package org.henick.lottoapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
public class Draw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "draw_system_id")
    private Long drawSystemId;

    @Column(name = "draw_date", nullable = false)
    private OffsetDateTime drawDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type", nullable = false)
    private GameType gameType;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "integer[]")
    List<Integer> results;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "integer[]")
    List<Integer> specialResults;

    public Draw(Long drawSystemId, OffsetDateTime drawDate, GameType gameType, List<Integer> results, List<Integer> specialResults) {
        this.drawSystemId = drawSystemId;
        this.drawDate = drawDate;
        this.gameType = gameType;
        this.results = results;
        this.specialResults = specialResults;
    }

    public Draw() {}
}

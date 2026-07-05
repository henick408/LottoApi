package org.henick.lottoapi.repository;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
    Optional<Draw> findTopByGameTypeOrderByDrawDateDesc(GameType gameType);
    Optional<Draw> findByGameTypeAndDrawDate(GameType gameType, OffsetDateTime drawDate);

    @Query("SELECT d FROM Draw d WHERE FUNCTION('DATE', d.drawDate) = :date")
    List<Draw> findByDrawDate(@Param("date") LocalDate drawDate);

    boolean existsByDrawDate(OffsetDateTime drawDate);
}

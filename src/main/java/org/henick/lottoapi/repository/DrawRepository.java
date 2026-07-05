package org.henick.lottoapi.repository;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;


@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
    Optional<Draw> findTopByGameTypeOrderByDrawDateDesc(GameType gameType);
    Optional<Draw> findByGameTypeAndDrawDate(GameType gameType, OffsetDateTime drawDate);
}

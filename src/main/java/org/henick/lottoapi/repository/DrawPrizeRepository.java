package org.henick.lottoapi.repository;

import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrawPrizeRepository extends JpaRepository<DrawPrize, Long> {
    Optional<DrawPrize> findByGameTypeAndDrawSystemId(GameType gameType, Long drawSystemId);
}

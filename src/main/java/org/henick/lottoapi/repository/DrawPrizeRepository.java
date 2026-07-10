package org.henick.lottoapi.repository;

import org.henick.lottoapi.model.DrawPrize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawPrizeRepository extends JpaRepository<DrawPrize, Long> {
}

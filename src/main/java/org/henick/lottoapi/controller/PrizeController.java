package org.henick.lottoapi.controller;

import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;
import org.henick.lottoapi.service.PrizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prizes")
public class PrizeController {

    private final PrizeService prizeService;

    public PrizeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @GetMapping("/{gameType}/{drawSystemId}")
    public ResponseEntity<DrawPrize> getPrize(@PathVariable String gameType, @PathVariable long drawSystemId) {
        DrawPrize drawPrize = prizeService.getPrize(GameType.from(gameType), drawSystemId);
        return ResponseEntity.ok(drawPrize);
    }

}

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
    private final ResponseMapper responseMapper;

    public PrizeController(PrizeService prizeService, ResponseMapper responseMapper) {
        this.prizeService = prizeService;
        this.responseMapper = responseMapper;
    }

    @GetMapping("/{gameType}/{drawSystemId}")
    public ResponseEntity<DrawPrizeResponse> getPrize(@PathVariable String gameType, @PathVariable long drawSystemId) {
        DrawPrize drawPrize = prizeService.getPrize(GameType.from(gameType), drawSystemId);
        DrawPrizeResponse drawPrizeResponse = responseMapper.toResponse(drawPrize);
        return ResponseEntity.ok(drawPrizeResponse);
    }

    @GetMapping("{gameType}")
    public ResponseEntity<DrawPrizeResponse> getPrizeForLastResult(@PathVariable String gameType) {
        DrawPrize drawPrize = prizeService.getPrizeForLastGame(GameType.from(gameType));
        DrawPrizeResponse drawPrizeResponse = responseMapper.toResponse(drawPrize);
        return ResponseEntity.ok(drawPrizeResponse);
    }

}

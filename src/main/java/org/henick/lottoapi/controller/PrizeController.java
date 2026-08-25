package org.henick.lottoapi.controller;

import org.henick.lottoapi.model.DrawPrize;
import org.henick.lottoapi.model.GameType;
import org.henick.lottoapi.service.PrizeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    public ResponseEntity<DrawPrizeResponse> getPrizeByDrawSystemId(@PathVariable String gameType, @PathVariable long drawSystemId) {
        DrawPrize drawPrize = prizeService.getPrize(GameType.from(gameType), drawSystemId);
        DrawPrizeResponse drawPrizeResponse = responseMapper.toResponse(drawPrize);
        return ResponseEntity.ok(drawPrizeResponse);
    }

    @GetMapping("{gameType}")
    public ResponseEntity<DrawPrizeResponse> getPrize(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate drawDate,
            @PathVariable String gameType
    ) {

        if (drawDate == null) {
            DrawPrize drawPrize = prizeService.getPrizeForLastGame(GameType.from(gameType));
            return ResponseEntity.ok(responseMapper.toResponse(drawPrize));
        }

        DrawPrize drawPrize = prizeService.getPrize(GameType.from(gameType), drawDate);
        DrawPrizeResponse drawPrizeResponse = responseMapper.toResponse(drawPrize);
        return ResponseEntity.ok(drawPrizeResponse);
    }

}

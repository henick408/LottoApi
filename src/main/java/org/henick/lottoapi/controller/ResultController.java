package org.henick.lottoapi.controller;

import org.henick.lottoapi.model.Draw;
import org.henick.lottoapi.model.GameType;
import org.henick.lottoapi.service.ResultService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/{gameType}")
    public ResponseEntity<Draw> getDrawsByGame(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate drawDate,
            @PathVariable String gameType
    ) {
        if (drawDate == null) {
            return ResponseEntity.ok(resultService.getLastResults(GameType.from(gameType)));
        }

        return ResponseEntity.ok(resultService.getResultsByDate(drawDate, GameType.from(gameType)));
    }

    @GetMapping
    public ResponseEntity<List<Draw>> getDraws(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate drawDate
    ) {
        if (drawDate == null) {
            return ResponseEntity.ok(resultService.getLastResults());
        }

        return ResponseEntity.ok(resultService.getResultsByDate(drawDate));
    }

}

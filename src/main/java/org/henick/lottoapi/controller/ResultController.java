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
    private final ResponseMapper responseMapper;

    public ResultController(ResultService resultService, ResponseMapper responseMapper) {
        this.resultService = resultService;
        this.responseMapper = responseMapper;
    }

    @GetMapping("/{gameType}")
    public ResponseEntity<DrawResponse> getDrawsByGame(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate drawDate,
            @PathVariable String gameType
    ) {
        if (drawDate == null) {
            Draw draw = resultService.getLastResults(GameType.from(gameType));
            return ResponseEntity.ok(responseMapper.toResponse(draw));
        }

        Draw draw = resultService.getResultsByDate(drawDate, GameType.from(gameType));
        return ResponseEntity.ok(responseMapper.toResponse(draw));
    }

    @GetMapping
    public ResponseEntity<List<DrawResponse>> getDraws(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate drawDate
    ) {
        if (drawDate == null) {
            List<Draw> draws = resultService.getLastResults();
            List<DrawResponse> drawResponses = draws.stream().map(responseMapper::toResponse).toList();
            return ResponseEntity.ok(drawResponses);
        }

        List<Draw> draws = resultService.getResultsByDate(drawDate);
        List<DrawResponse> drawResponses = draws.stream().map(responseMapper::toResponse).toList();
        return ResponseEntity.ok(drawResponses);
    }

}

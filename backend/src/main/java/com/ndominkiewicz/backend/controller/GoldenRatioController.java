package com.ndominkiewicz.backend.controller;

import com.ndominkiewicz.backend.result.GoldenRatioResult;
import com.ndominkiewicz.backend.service.GoldenRatioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GoldenRatioController {
    private final GoldenRatioService goldenRatioService;

    public GoldenRatioController(GoldenRatioService goldenRatioService) {
        this.goldenRatioService = goldenRatioService;
    }
    @GetMapping("/goldenratio")
    public GoldenRatioResult solve(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam double e,
            @RequestParam String equation
    ) {
        return goldenRatioService.solve(a, b ,e, equation);
    }
    @GetMapping("/goldenratioExample")
    public GoldenRatioResult solve() {
        return goldenRatioService.solve();
    }
}

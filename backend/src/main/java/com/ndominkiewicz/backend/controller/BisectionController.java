package com.ndominkiewicz.backend.controller;

import com.ndominkiewicz.backend.model.BisectionResult;
import com.ndominkiewicz.backend.service.BisectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BisectionController {
    private final BisectionService bisectionService;
    public BisectionController(BisectionService bisectionService) {
        this.bisectionService = bisectionService;
    }
    @GetMapping("/bisectionTest")
    public BisectionResult solve() {
        return bisectionService.solve();
    }
    @GetMapping("/bisection")
    public BisectionResult solve(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam double e,
            @RequestParam String equation
        ) {
        return bisectionService.solve(a, b, e, equation);
    }
}

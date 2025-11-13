package com.ndominkiewicz.backend.controller;

import com.ndominkiewicz.backend.result.FibonacciResult;
import com.ndominkiewicz.backend.service.FibonacciService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class FibonacciController {
    private final FibonacciService fibonacciService;
    public FibonacciController(FibonacciService fibonacciService) {
        this.fibonacciService= fibonacciService;
    }
    @GetMapping("/fibonacci")
    public FibonacciResult solve(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam double e,
            @RequestParam String equation
    ) {
        return fibonacciService.solve(a, b, e, equation);
    }
    @GetMapping("/fibonacciExample")
    public FibonacciResult solve() {
        return fibonacciService.solve();
    }
}

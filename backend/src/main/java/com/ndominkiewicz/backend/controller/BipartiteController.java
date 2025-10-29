package com.ndominkiewicz.backend.controller;

import com.ndominkiewicz.backend.model.BipartiteResult;
import com.ndominkiewicz.backend.service.BipartiteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BipartiteController {
    @GetMapping("/bisection")
    public BipartiteResult solve(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam double e,
            @RequestParam String equation) {
        return BipartiteService.solve(a, b, e, equation);
    }
}

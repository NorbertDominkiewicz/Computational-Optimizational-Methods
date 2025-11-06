package com.ndominkiewicz.backend.controller;

import com.ndominkiewicz.backend.model.NewtonResult;
import com.ndominkiewicz.backend.model.SecantResult;
import com.ndominkiewicz.backend.service.SecantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SecantController {
    private final SecantService secantService;

    public SecantController(SecantService secantService) {
        this.secantService = secantService;
    }

    @GetMapping("/secant")
    public SecantResult solve(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam double e,
            @RequestParam String firstDerivative,
            @RequestParam String thirdDerivative
    ) {
        return secantService.solve(a, b, e, firstDerivative, thirdDerivative);
    }
    @GetMapping("/secantExample")
    public SecantResult solve() {
        return secantService.solve();
    }
}

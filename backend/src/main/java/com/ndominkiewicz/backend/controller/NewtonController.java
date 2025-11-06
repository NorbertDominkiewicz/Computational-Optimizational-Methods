package com.ndominkiewicz.backend.controller;

import com.ndominkiewicz.backend.model.NewtonResult;
import com.ndominkiewicz.backend.service.NewtonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class NewtonController {
    private final NewtonService newtonService;

    public NewtonController(NewtonService newtonService) {
        this.newtonService = newtonService;
    }

    @GetMapping("/newton")
    public NewtonResult solve(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam double e,
            @RequestParam String firstDerivative,
            @RequestParam String secondDerivative,
            @RequestParam String thirdDerivative
    ) {
        return newtonService.solve(a, b, e, firstDerivative, secondDerivative, thirdDerivative);
    }
    @GetMapping("/newtonExample")
    public NewtonResult solve() {
        return newtonService.solve();
    }
}

package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Response.HealthFinderResponse;
import com.hospital.hospitalmanagementsystem.Service.HealthFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthFinderController {
    @Autowired
    private HealthFinderService healthFinderService;

    @GetMapping("/api/healthfinder")
    public Mono<String> getHealthFinderData(
            @RequestParam int age,
            @RequestParam String sex,
            @RequestParam String pregnant,
            @RequestParam String sexuallyActive,
            @RequestParam String tobaccoUse) {
        return healthFinderService.getHealthFinderData(age, sex, pregnant, sexuallyActive, tobaccoUse);
    }
}
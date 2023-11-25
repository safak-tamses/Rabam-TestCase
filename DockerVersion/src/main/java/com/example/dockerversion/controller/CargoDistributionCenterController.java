package com.example.dockerversion.controller;

import com.example.dockerversion.model.DTO.GeneralResponse;
import com.example.dockerversion.service.PackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cargo-distribution-center")
@AllArgsConstructor
public class CargoDistributionCenterController {
    private final PackService packService;

    @GetMapping("/daily-deliver")
    public ResponseEntity<GeneralResponse> dailyDelivered() {
        return new ResponseEntity(packService.showDailyDeliveringPackage(), HttpStatus.OK);
    }

    @GetMapping("/daily-receivePackage")
    public ResponseEntity<GeneralResponse> dailyReceivePackage() {
        return new ResponseEntity(packService.showDailyDeliveredPackage(), HttpStatus.OK);
    }
}

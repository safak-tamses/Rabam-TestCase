package com.example.rabam.controller;

import com.example.rabam.model.DTO.GeneralResponse;
import com.example.rabam.service.PackageService;
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
    private final PackageService packageService;

    @GetMapping("/daily-deliver")
    public ResponseEntity<GeneralResponse> dailyDelivered() {
        return new ResponseEntity(packageService.showDailyDeliveringPackage(), HttpStatus.OK);
    }

    @GetMapping("/daily-receivePackage")
    public ResponseEntity<GeneralResponse> dailyReceivePackage() {
        return new ResponseEntity(packageService.showDailyDeliveredPackage(), HttpStatus.OK);
    }
}

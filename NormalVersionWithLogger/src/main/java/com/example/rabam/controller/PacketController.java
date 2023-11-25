package com.example.rabam.controller;

import com.example.rabam.generic.GenericResponse;
import com.example.rabam.model.DTO.PackageCreateRequest;
import com.example.rabam.model.DTO.PackageUpdateRequest;
import com.example.rabam.service.PackageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/packet")
public class PacketController {
    private final PackageService packageService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> packetCreate(@RequestBody PackageCreateRequest packageCreateRequest) {
        return new ResponseEntity<>(packageService.createPackage(packageCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<GenericResponse> packetShow(@PathVariable Long id) {
        return new ResponseEntity<>(packageService.readPackage(id), HttpStatus.OK);
    }

    @GetMapping("/show/all")
    public ResponseEntity<GenericResponse> packetShowAll() {
        return new ResponseEntity<>(packageService.readAllPackage(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse> packetUpdate(@RequestBody PackageUpdateRequest packageUpdateRequest) {
        return new ResponseEntity<>(packageService.updatePackage(packageUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> packetdelete(@PathVariable Long id) {
        return new ResponseEntity<>(packageService.deletePackage(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<GenericResponse> packetDeleteAll() {
        return new ResponseEntity<>(packageService.deleteAllPackage(), HttpStatus.OK);
    }

    @GetMapping("/show/estimate-time/{packageId}")
    public ResponseEntity<GenericResponse> showEstimateTime(
            @PathVariable Long packageId
    ) {
        return new ResponseEntity<>(packageService.findEstimatedDeliveryTime(packageId), HttpStatus.OK);
    }

}

package com.example.dockerversion.controller;

import com.example.dockerversion.generic.GenericResponse;
import com.example.dockerversion.model.DTO.PackageCreateRequest;
import com.example.dockerversion.model.DTO.PackageUpdateRequest;
import com.example.dockerversion.service.PackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/packet")
public class PacketController {
    private final PackService packService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> packetCreate(@RequestBody PackageCreateRequest packageCreateRequest) {
        return new ResponseEntity<>(packService.createPackage(packageCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<GenericResponse> packetShow(@PathVariable Long id) {
        return new ResponseEntity<>(packService.readPackage(id), HttpStatus.OK);
    }

    @GetMapping("/show/all")
    public ResponseEntity<GenericResponse> packetShowAll() {
        return new ResponseEntity<>(packService.readAllPackage(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse> packetUpdate(@RequestBody PackageUpdateRequest packageUpdateRequest) {
        return new ResponseEntity<>(packService.updatePackage(packageUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> packetdelete(@PathVariable Long id) {
        return new ResponseEntity<>(packService.deletePackage(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<GenericResponse> packetDeleteAll() {
        return new ResponseEntity<>(packService.deleteAllPackage(), HttpStatus.OK);
    }

    @GetMapping("/show/estimate-time/{packageId}")
    public ResponseEntity<GenericResponse> showEstimateTime(
            @PathVariable Long packageId
    ) {
        return new ResponseEntity<>(packService.findEstimatedDeliveryTime(packageId), HttpStatus.OK);
    }

}


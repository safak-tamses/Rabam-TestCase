package com.example.rabam.controller;

import com.example.rabam.generic.GenericResponse;
import com.example.rabam.model.DTO.CourierCreateRequest;
import com.example.rabam.model.DTO.CourierReadRequest;
import com.example.rabam.model.DTO.CourierUpdateRequest;
import com.example.rabam.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/courier")
public class CourierController {
    private final CourierService courierService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> courierCreate(@RequestBody CourierCreateRequest courierCreateRequest) {
        return new ResponseEntity<>(courierService.createCourier(courierCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<GenericResponse> courierShow(@PathVariable Long id) {
        return new ResponseEntity<>(courierService.readCourier(id), HttpStatus.OK);
    }

    @GetMapping("/show/all")
    public ResponseEntity<GenericResponse> courierShowAll() {
        return new ResponseEntity<>(courierService.readAllCourier(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse> courierUpdate(@RequestBody CourierUpdateRequest customerUpdateRequest) {
        return new ResponseEntity<>(courierService.updateCourier(customerUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> courierDelete(@PathVariable Long id) {
        return new ResponseEntity<>(courierService.deleteCourier(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<GenericResponse> courierDeleteAll() {
        return new ResponseEntity<>(courierService.deleteAllCourier(), HttpStatus.OK);
    }

    @PostMapping("/update/coordinate")
    public ResponseEntity<GenericResponse> updateCourierCoordinate(
            @RequestParam Long courierId,
            @RequestParam Double currentLongitude,
            @RequestParam Double currentLatitude,
            @RequestParam Double currentSpeed
    ) {
        return new ResponseEntity<>(
                courierService.setCourierCoordinate(courierId, currentLongitude, currentLatitude, currentSpeed),
                HttpStatus.OK);
    }

    @PostMapping("/add/packet")
    public ResponseEntity<GenericResponse> receivePackage(
            @RequestParam Long courierId
    ) {
        return new ResponseEntity<>(
                courierService.receivePackage(courierId), HttpStatus.OK
        );
    }

    @PostMapping("/packet/delivered")
    public ResponseEntity<GenericResponse> deliveredPackage(
            @RequestParam Long courierId
    ) {
        return new ResponseEntity<>(
                courierService.packageDelivered(courierId), HttpStatus.OK
        );
    }

    @GetMapping("/show/current-status/{id}")
    public ResponseEntity<GenericResponse> showCourierCurrentStatus(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(
                courierService.showCourierCurrentStatus(id), HttpStatus.OK
        );
    }

}

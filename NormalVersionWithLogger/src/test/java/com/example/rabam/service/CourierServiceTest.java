package com.example.rabam.service;

import com.example.rabam.generic.GenericResponse;
import com.example.rabam.model.Center;
import com.example.rabam.model.Courier;
import com.example.rabam.model.DTO.CourierCreateRequest;
import com.example.rabam.model.DTO.CourierDTO;
import com.example.rabam.repository.CourierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CourierServiceTest {

    @InjectMocks
    private CourierService courierService;

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private PackageService packageService;

    @Mock
    private CenterService centerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCourier() {

    }

    @Test
    void createCourier() {

    }

    @Test
    void updateCourier() {
    }

    @Test
    void readAllCourier() {
    }

    @Test
    void readCourier() {
    }

    @Test
    void deleteCourier() {
    }

    @Test
    void deleteAllCourier() {
    }

    @Test
    void setCourierCoordinate() {
    }

    @Test
    void receivePackage() {
    }

    @Test
    void packageDelivered() {
    }

    @Test
    void showCourierCurrentStatus() {
    }
}
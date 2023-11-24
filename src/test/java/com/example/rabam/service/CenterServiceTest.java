package com.example.rabam.service;

import com.example.rabam.model.Center;
import com.example.rabam.repository.CenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CenterServiceTest {

    @InjectMocks
    private CenterService centerService;

    @Mock
    private CenterRepository centerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void initializeCenter() {
        when(centerRepository.existsById(1L)).thenReturn(false);

        centerService.initializeCenter();

        verify(centerRepository, times(1)).save(any());
    }

    @Test
    void getMainCenter() {
        Center mockCenter = new Center();
        when(centerRepository.findById(1L)).thenReturn(Optional.of(mockCenter));

        Center result = centerService.getMainCenter();

        assertNotNull(result);
        assertSame(mockCenter, result);
    }
}
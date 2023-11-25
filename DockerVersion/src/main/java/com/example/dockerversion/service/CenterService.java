package com.example.dockerversion.service;

import com.example.dockerversion.error.CenterNotCreatedException;
import com.example.dockerversion.model.Center;
import com.example.dockerversion.repository.CenterRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CenterService {
    private final CenterRepository centerRepository;

    @Autowired
    public CenterService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;

    }

    @PostConstruct
    public void initializeCenter() {
        try {
            if (!centerRepository.existsById(1L)) {
                Center center = Center.builder()
                        .courierList(new ArrayList<>())
                        .packageList(new ArrayList<>())
                        .build();

                centerRepository.save(center);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    protected Center getMainCenter() {
        try {
            return centerRepository.findById(1L).orElseThrow(CenterNotCreatedException::new);
        } catch (Exception e) {
            throw new CenterNotCreatedException();
        }
    }



}

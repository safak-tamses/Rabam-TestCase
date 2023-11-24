package com.example.rabam.service;

import com.example.rabam.error.CenterNotCreatedException;
import com.example.rabam.model.Center;
import com.example.rabam.repository.CenterRepository;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CenterService {
    private final CenterRepository centerRepository;
    private static final Logger logger = LogManager.getLogger(CourierService.class);

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
                logger.info("Initial center setup completed successfully.");
            }
        } catch (Exception e) {
            logger.info("Error during initial center setup.", e);
        }
    }

    protected Center getMainCenter() {
        try {
            return centerRepository.findById(1L).orElseThrow(CenterNotCreatedException::new);
        } catch (Exception e) {
            logger.error("Error while getting the main center.", e);
            throw new CenterNotCreatedException();
        }
    }



}

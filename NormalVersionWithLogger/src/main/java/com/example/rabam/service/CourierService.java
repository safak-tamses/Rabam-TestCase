package com.example.rabam.service;

import com.example.rabam.error.*;
import com.example.rabam.generic.GenericResponse;
import com.example.rabam.model.Center;
import com.example.rabam.model.Courier;
import com.example.rabam.model.DTO.*;
import com.example.rabam.model.Package;
import com.example.rabam.repository.CourierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@AllArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final PackageService packageService;
    private final CenterService centerService;
    private static final Logger logger = LogManager.getLogger(CourierService.class);

    private Courier getCourierById(Long id) {
        try {
            return courierRepository.findById(id).orElseThrow(CourierNotFoundException::new);
        } catch (Exception e) {
            throw new CourierNotFoundException();
        }
    }

    private void saveCourier(Courier courier) {
        try {
            courierRepository.save(courier);
            logger.info("Courier created successfully. ID: {}", courier.getId());
        } catch (Exception e) {
            logger.info("Error while creating courier", e);
            throw new CourierSavedException();
        }
    }

    public GenericResponse<CourierDTO> createCourier(CourierCreateRequest request) {
        try {
            Center mainCenter = centerService.getMainCenter();
            Courier courier = Courier.builder()
                    .name(request.getName())
                    .surname(request.getSurname())
                    .phone(request.getPhone())
                    .createdDate(new Date())
                    .center(mainCenter)
                    .currentSpeed(0.0)
                    .currentLongitude(mainCenter.getLongitude())
                    .currentLatitude(mainCenter.getLatitude())
                    .hasItBeenDelivered(Boolean.FALSE)
                    .build();
            CourierDTO courierDTO = new CourierDTO(
                    courier.getName(),
                    courier.getSurname(),
                    courier.getPhone(),
                    courier.getCreatedDate(),
                    courier.getUpdateDate()
            );

            saveCourier(courier);
            logger.info("Courier created successfully. Name: {}, Surname: {}, Phone: {}", courier.getName(), courier.getSurname(), courier.getPhone());
            return new GenericResponse<>(courierDTO, Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while creating courier", e);
            throw new CourierSavedException();
        }
    }

    public GenericResponse<CourierDTO> updateCourier(CourierUpdateRequest request) {
        try {
            Courier courier = getCourierById(request.getCourierId());
            courier.setName(request.getName());
            courier.setSurname(request.getSurname());
            courier.setPhone(request.getPhone());
            courier.setUpdateDate(new Date());
            saveCourier(courier);
            CourierDTO courierDTO = new CourierDTO(
                    courier.getName(),
                    courier.getSurname(),
                    courier.getPhone(),
                    courier.getCreatedDate(),
                    courier.getUpdateDate()
            );
            logger.info("Courier updated successfully. Courier ID: {}", request.getCourierId());
            return new GenericResponse<>(courierDTO, Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while updating courier. Courier ID: {}", request.getCourierId(), e);
            throw new CourierUpdateException();
        }
    }

    public GenericResponse<List<CourierDTO>> readAllCourier() {
        try {
            List<Courier> courierList = courierRepository.findAll();
            logger.info("All couriers retrieved successfully. Total count: {}", courierList.size());
            return new GenericResponse<>(
                    courierList.stream()
                            .map(courier -> new CourierDTO(
                                    courier.getName(),
                                    courier.getSurname(),
                                    courier.getPhone(),
                                    courier.getCreatedDate(),
                                    courier.getUpdateDate()
                            )).collect(Collectors.toList())
                    , Boolean.TRUE
            );
        } catch (Exception e) {
            logger.info("Error while retrieving all couriers", e);
            throw new CourierReadException();
        }
    }

    public GenericResponse<CourierDTO> readCourier(Long id) {
        try {
            Courier courier = getCourierById(id);
            logger.info("Courier with ID {} retrieved successfully.", id);
            CourierDTO courierDTO = new CourierDTO(
                    courier.getName(),
                    courier.getSurname(),
                    courier.getPhone(),
                    courier.getCreatedDate(),
                    courier.getUpdateDate()
            );
            return new GenericResponse<>(
                    courierDTO
                    , Boolean.TRUE
            );
        } catch (Exception e) {
            logger.info("Error while retrieving courier with ID {}", id, e);
            throw new CourierNotFoundException();
        }
    }

    public GenericResponse<GeneralResponse> deleteCourier(Long id) {
        try {
            courierRepository.deleteById(id);
            logger.info("Courier with ID {} deleted successfully.", id);
            return new GenericResponse<>(GeneralResponse.builder().responseMessage("Courier deleted successfully").build(), Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while deleting courier with ID {}", id, e);
            throw new CourierDeleteException();
        }
    }

    public GenericResponse<GeneralResponse> deleteAllCourier() {
        try {
            courierRepository.deleteAll();
            logger.info("All couriers deleted successfully.");
            return new GenericResponse<>(GeneralResponse.builder().responseMessage("All couriers deleted successfully").build(), Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while deleting all couriers.", e);
            throw new CourierDeleteException();
        }
    }

    public GenericResponse<GeneralResponse> setCourierCoordinate(Long courierId, Double lon, Double lat, Double speed) {
        try {
            Courier courier = getCourierById(courierId);
            courier.setCurrentLatitude(lat);
            courier.setCurrentLongitude(lon);
            courier.setCurrentSpeed(speed);
            saveCourier(courier);
            String logMessage = String.format("Courier coordinate info updated successfully. Courier ID: %d, Latitude: %f, Longitude: %f, Speed: %f", courierId, lat, lon, speed);
            logger.info(logMessage);
        } catch (Exception e) {
            logger.info("Error while updating courier coordinate info.", e);
            throw new CourierNotFoundException();
        }
        GeneralResponse generalResponse = new GeneralResponse("Courier cordinate info updated successfully.");
        return new GenericResponse<>(generalResponse, Boolean.TRUE);
    }

    public GenericResponse<CourierDTO> receivePackage(Long courierId) {
        try {
            Package aPackage = packageService.getRandomPackage();
            Courier courier = getCourierById(courierId);
            if (courier.getHasItBeenDelivered() == Boolean.FALSE) {

                courier.setDispatchedPackage(aPackage);
                courier.setHasItBeenDelivered(Boolean.TRUE);
                saveCourier(courier);

                packageService.updatePackageStatus(aPackage.getId());

                CourierDTO courierDTO = new CourierDTO(
                        courier.getName(),
                        courier.getSurname(),
                        courier.getPhone(),
                        courier.getCreatedDate(),
                        courier.getUpdateDate()
                );
                String logMessage = String.format("Package received successfully by courier. Courier ID: %d, Package ID: %d", courierId, aPackage.getId());
                logger.info(logMessage);

                return new GenericResponse<>(
                        courierDTO, Boolean.TRUE
                );
            } else {
                logger.info("Courier not available for package delivery. Courier ID: {}", courierId);
                throw new CourierNotAvailableException();
            }

        } catch (Exception e) {
            logger.info("Error while receiving package by courier. Courier ID: {}", courierId, e);
            throw new CourierNotAvailableException();
        }
    }

    public GenericResponse<GeneralResponse> packageDelivered(Long courierId) {
        try {
            Courier courier = getCourierById(courierId);
            if (courier.getHasItBeenDelivered() == Boolean.TRUE &&
                    courier.getDispatchedPackage().getShippingStatus() == Boolean.TRUE
            ) {
                packageService.packageDelivered(courier.getDispatchedPackage());
                courier.setDispatchedPackage(null);
                courier.setHasItBeenDelivered(Boolean.FALSE);
                courierRepository.save(courier);
                GeneralResponse generalResponse = new GeneralResponse(
                        "Packet delivered successfully."
                );
                String logMessage = String.format("Package delivered successfully by courier. Courier ID: %d, Package ID: %d", courierId, courier.getDispatchedPackage().getId());
                logger.info(logMessage);
                return new GenericResponse<>(generalResponse, Boolean.TRUE);
            } else {
                logger.info("Package delivery failed. Courier ID: {}", courierId);
                throw new PackageDeleteException();
            }
        } catch (Exception e) {
            logger.info("Error while delivering package by courier. Courier ID: {}", courierId, e);
            throw new PackageUpdateException();
        }
    }

    public GenericResponse<CourierStatusDTO> showCourierCurrentStatus(Long id) {
        try {
            Courier courier = getCourierById(id);
            CourierStatusDTO courierStatusDTO = new CourierStatusDTO(
                    courier.getName(),
                    courier.getSurname(),
                    courier.getPhone(),
                    courier.getUpdateDate(),
                    courier.getHasItBeenDelivered(),
                    courier.getCurrentLongitude(),
                    courier.getCurrentLatitude(),
                    courier.getCurrentSpeed()
            );
            String logMessage = String.format("Courier current status retrieved. Courier ID: %d, Name: %s, Surname: %s", id, courier.getName(), courier.getSurname());
            logger.info(logMessage);
            return new GenericResponse<>(courierStatusDTO, Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while retrieving courier status. Courier ID: {}", id, e);
            throw new CourierNotFoundException();
        }
    }
}

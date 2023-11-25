package com.example.dockerversion.service;

import com.example.dockerversion.error.*;
import com.example.dockerversion.generic.GenericResponse;
import com.example.dockerversion.model.Center;
import com.example.dockerversion.model.Courier;
import com.example.dockerversion.model.DTO.*;
import com.example.dockerversion.model.Package;
import com.example.dockerversion.repository.CourierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final PackService packService;
    private final CenterService centerService;

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
        } catch (Exception e) {
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
            return new GenericResponse<>(courierDTO, Boolean.TRUE);
        } catch (Exception e) {
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
            return new GenericResponse<>(courierDTO, Boolean.TRUE);
        } catch (Exception e) {
            throw new CourierUpdateException();
        }
    }

    public GenericResponse<List<CourierDTO>> readAllCourier() {
        try {
            List<Courier> courierList = courierRepository.findAll();
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
            throw new CourierReadException();
        }
    }

    public GenericResponse<CourierDTO> readCourier(Long id) {
        try {
            Courier courier = getCourierById(id);
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
            throw new CourierNotFoundException();
        }
    }

    public GenericResponse<GeneralResponse> deleteCourier(Long id) {
        try {
            courierRepository.deleteById(id);
            return new GenericResponse<>(GeneralResponse.builder().responseMessage("Courier deleted successfully").build(), Boolean.TRUE);
        } catch (Exception e) {
            throw new CourierDeleteException();
        }
    }

    public GenericResponse<GeneralResponse> deleteAllCourier() {
        try {
            courierRepository.deleteAll();
            return new GenericResponse<>(GeneralResponse.builder().responseMessage("All couriers deleted successfully").build(), Boolean.TRUE);
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new CourierNotFoundException();
        }
        GeneralResponse generalResponse = new GeneralResponse("Courier cordinate info updated successfully.");
        return new GenericResponse<>(generalResponse, Boolean.TRUE);
    }

    public GenericResponse<CourierDTO> receivePackage(Long courierId) {
        try {
            Package aPackage = packService.getRandomPackage();
            Courier courier = getCourierById(courierId);
            if (courier.getHasItBeenDelivered() == Boolean.FALSE) {

                courier.setDispatchedPackage(aPackage);
                courier.setHasItBeenDelivered(Boolean.TRUE);
                saveCourier(courier);

                packService.updatePackageStatus(aPackage.getId());

                CourierDTO courierDTO = new CourierDTO(
                        courier.getName(),
                        courier.getSurname(),
                        courier.getPhone(),
                        courier.getCreatedDate(),
                        courier.getUpdateDate()
                );

                return new GenericResponse<>(
                        courierDTO, Boolean.TRUE
                );
            } else {
                throw new CourierNotAvailableException();
            }

        } catch (Exception e) {
            throw new CourierNotAvailableException();
        }
    }

    public GenericResponse<GeneralResponse> packageDelivered(Long courierId) {
        try {
            Courier courier = getCourierById(courierId);
            if (courier.getHasItBeenDelivered() == Boolean.TRUE &&
                    courier.getDispatchedPackage().getShippingStatus() == Boolean.TRUE
            ) {
                packService.packageDelivered(courier.getDispatchedPackage());
                courier.setDispatchedPackage(null);
                courier.setHasItBeenDelivered(Boolean.FALSE);
                courierRepository.save(courier);
                GeneralResponse generalResponse = new GeneralResponse(
                        "Packet delivered successfully."
                );
                return new GenericResponse<>(generalResponse, Boolean.TRUE);
            } else {
                throw new PackageDeleteException();
            }
        } catch (Exception e) {
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
            return new GenericResponse<>(courierStatusDTO, Boolean.TRUE);
        } catch (Exception e) {
            throw new CourierNotFoundException();
        }
    }
}


package com.example.rabam.service;

import com.example.rabam.error.*;
import com.example.rabam.generic.GenericResponse;
import com.example.rabam.model.DTO.*;
import com.example.rabam.model.Package;
import com.example.rabam.repository.PackageRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PackageService {
    private final PackageRepository packageRepository;
    private final CenterService centerService;
    private final SideService sideService;
    private static final Logger logger = LogManager.getLogger(CourierService.class);

    public GenericResponse<PackageDTO> createPackage(PackageCreateRequest request) {
        try {
            Package aPackage = Package.builder()
                    .center(centerService.getMainCenter())
                    .destinationAddress(request.getDestinationAddress())
                    .recipientName(request.getRecipientName())
                    .shippingStatus(Boolean.FALSE)
                    .isDelivered(Boolean.FALSE)
                    .build();
            packageRepository.save(aPackage);
            PackageDTO packageDTO = new PackageDTO(
                    aPackage.getId(),
                    aPackage.getRecipientName(),
                    aPackage.getDestinationAddress(),
                    aPackage.getShippingStatus(),
                    aPackage.getIsDelivered(),
                    aPackage.getEstimatedArrivalTime()
            );
            logger.info("Package created successfully. Package ID: {}", aPackage.getId());
            return new GenericResponse<>(packageDTO, Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while creating a package.", e);
            throw new PackageSavedException();
        }
    }

    public GenericResponse<PackageDTO> updatePackage(PackageUpdateRequest request) {
        try {
            Package aPackage = packageRepository.findById(request.getId()).orElseThrow(PackageUpdateException::new);
            aPackage.setDestinationAddress(request.getDestinationAddress());
            aPackage.setRecipientName(request.getRecipientName());
            packageRepository.save(aPackage);
            PackageDTO packageDTO = new PackageDTO(
                    aPackage.getId(),
                    aPackage.getRecipientName(),
                    aPackage.getDestinationAddress(),
                    aPackage.getShippingStatus(),
                    aPackage.getIsDelivered(),
                    aPackage.getEstimatedArrivalTime()
            );
            logger.info("Package updated successfully. Package ID: {}", aPackage.getId());
            return new GenericResponse<>(packageDTO, Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while updating a package.", e);
            throw new PackageUpdateException();
        }
    }

    public GenericResponse<List<PackageDTO>> readAllPackage() {
        try {
            List<PackageDTO> packageDTOList = packageRepository.findAll().stream()
                    .map(packet -> new PackageDTO(
                            packet.getId(),
                            packet.getRecipientName(),
                            packet.getDestinationAddress(),
                            packet.getShippingStatus(),
                            packet.getIsDelivered(),
                            packet.getEstimatedArrivalTime()
                    )).collect(Collectors.toList());
            logger.info("All packages read successfully. Number of packages: {}", packageDTOList.size());
            return new GenericResponse<>(
                    packageDTOList
                    , Boolean.TRUE
            );
        } catch (Exception e) {
            logger.info("Error while reading all packages.", e);
            throw new PackageReadException();
        }
    }

    public GenericResponse<PackageDTO> readPackage(Long id) {
        try {
            Package aPackage = packageRepository.findById(id).orElseThrow(PackageNotFoundException::new);
            PackageDTO packageDTO = new PackageDTO(
                    aPackage.getId(),
                    aPackage.getRecipientName(),
                    aPackage.getDestinationAddress(),
                    aPackage.getShippingStatus(),
                    aPackage.getIsDelivered(),
                    aPackage.getEstimatedArrivalTime()
            );
            logger.info("Package with ID {} read successfully.", id);
            return new GenericResponse<>(
                    packageDTO
                    , Boolean.TRUE
            );
        } catch (Exception e) {
            logger.info("Error while reading package with ID {}.", id, e);
            throw new PackageNotFoundException();
        }
    }

    public GenericResponse<GeneralResponse> deletePackage(Long id) {
        try {
            packageRepository.deleteById(id);
            logger.info("Package with ID {} deleted successfully.", id);
            return new GenericResponse<>(GeneralResponse.builder().responseMessage("Packet deleted successfully").build(), Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while deleting package with ID {}.", id, e);
            throw new PackageDeleteException();
        }
    }

    public GenericResponse<GeneralResponse> deleteAllPackage() {
        try {
            packageRepository.deleteAll();
            logger.info("All packages deleted successfully.");
            return new GenericResponse<>(GeneralResponse.builder().responseMessage("All packets deleted successfully").build(), Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while deleting all packages.", e);
            throw new PackageDeleteException();
        }
    }

    protected Package getPackageById(Long id) {
        try {
            return packageRepository.findById(id).orElseThrow(PackageNotFoundException::new);
        } catch (Exception e) {
            logger.info("Error while getting package by ID: {}", id, e);
            throw new PackageNotFoundException();
        }
    }

    protected Package getRandomPackage() {
        try {
            List<Package> packageList = getAllPackage();
            return packageList.stream()
                    .filter(packet -> packet.getShippingStatus() == Boolean.FALSE && packet.getIsDelivered() == Boolean.FALSE)
                    .skip(new Random().nextInt(packageList.size()))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            logger.info("Error while getting random package", e);
            throw new PackageNotFoundException();
        }
    }

    protected void updatePackageStatus(Long packetId) {
        try {
            Package aPackage = packageRepository.findById(packetId).orElseThrow(PackageNotFoundException::new);
            aPackage.setShippingStatus(Boolean.TRUE);
            aPackage.setDeliveryDate(new Date());
            savePackage(aPackage);
        } catch (Exception e) {
            logger.info("Error while updating package status", e);
            throw new PackageUpdateException();
        }
    }

    private void savePackage(Package aPackage) {
        try {
            System.out.println(aPackage.getId());
            packageRepository.save(aPackage);
        } catch (Exception e) {
            logger.info("Error while saving package", e);
            throw new PackageSavedException();
        }
    }

    protected void deletePackage(Package aPackage) {
        try {
            packageRepository.delete(aPackage);
        } catch (Exception e) {
            throw new PackageDeleteException();
        }
    }

    protected void packageDelivered(Package aPackage) {
        try {
            aPackage.setIsDelivered(Boolean.TRUE);
            aPackage.setShippingStatus(Boolean.FALSE);
            aPackage.setDateDelivered(new Date());
            packageRepository.save(aPackage);
        } catch (Exception e) {
            logger.info("Error while marking package as delivered", e);
            throw new PackageUpdateException();
        }
    }

    protected List<Package> getAllPackage() {
        try {
            return packageRepository.findAll();
        } catch (Exception e) {
            logger.info("Error while retrieving all packages", e);
            throw new PackageReadException();
        }
    }

    public GenericResponse<GeneralResponse> showDailyDeliveringPackage() {
        try {
            List<Package> packageList = getAllPackage();
            Date today = new Date();

            List<Package> todayDeliveryList = packageList.stream()
                    .filter(packet -> Objects.nonNull(packet.getDeliveryDate()) && isSameDay(packet.getDeliveryDate(), today))
                    .toList();

            GeneralResponse generalResponse = new GeneralResponse(
                    todayDeliveryList.size() + " number of deliveries have departed today"
            );
            return new GenericResponse<>(generalResponse, Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while showing daily delivering packages", e);
            throw new RuntimeException();
        }
    }

    public GenericResponse<GeneralResponse> showDailyDeliveredPackage() {
        try {
            List<Package> packageList = getAllPackage();
            Date today = new Date();

            List<Package> todayDeliveryList = packageList.stream()
                    .filter(packet -> Objects.nonNull(packet.getDateDelivered()) && isSameDay(packet.getDateDelivered(), today))
                    .toList();

            GeneralResponse generalResponse = new GeneralResponse(
                    todayDeliveryList.size() + " number of deliveries were delivered today"
            );

            return new GenericResponse<>(generalResponse, Boolean.TRUE);
        } catch (Exception e) {
            logger.info("Error while showing daily delivered packages", e);
            throw new RuntimeException();
        }
    }

    private boolean isSameDay(Date date1, Date date2) {
        return date1.getYear() == date2.getYear() &&
                date1.getMonth() == date2.getMonth() &&
                date1.getDate() == date2.getDate();
    }

    public GenericResponse<GeneralResponse> findEstimatedDeliveryTime(Long packageId) {
        try {
            Package aPackage = getPackageById(packageId);
            if (!aPackage.getShippingStatus()) {
                GeneralResponse generalResponse = new GeneralResponse(
                        "The package has not been delivered yet"
                );
                return new GenericResponse<>(generalResponse, Boolean.TRUE);
            } else {
                String address = aPackage.getDestinationAddress();
                if (aPackage.getDeliveryCourier() != null) {
                    AdressCoordinate coordinate = sideService.findCoordinate(address);
                    Double currentLat = aPackage.getDeliveryCourier().getCurrentLatitude();
                    Double curentLon = aPackage.getDeliveryCourier().getCurrentLongitude();
                    Double currentSpeed = aPackage.getDeliveryCourier().getCurrentSpeed();

                    Double destinationLat = coordinate.getLatitude();
                    Double destinationLon = coordinate.getLongitude();


                    Double distance = sideService.haversine(currentLat, curentLon, destinationLat, destinationLon);
                    //saat cinsinden
                    Double time = distance / currentSpeed;
                    GeneralResponse generalResponse = new GeneralResponse(
                            "The package will be delivered to the recipient after " + time + " hours"
                    );
                    logger.info("Package with ID {} has not been delivered yet.", packageId);
                    return new GenericResponse<>(generalResponse, Boolean.TRUE);

                } else {
                    logger.info("Finished finding estimated delivery time for package with ID: {}", packageId);
                    throw new PackageDeliveringStatusException();
                }
            }
        } catch (Exception e) {
            logger.info("Error while finding estimated delivery time for package with ID: {}", packageId, e);
            throw new PackageDeliveringStatusException();
        }
    }
}

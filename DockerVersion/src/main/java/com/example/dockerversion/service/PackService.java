package com.example.dockerversion.service;

import com.example.dockerversion.error.*;
import com.example.dockerversion.generic.GenericResponse;
import com.example.dockerversion.model.DTO.*;
import com.example.dockerversion.model.Package;
import com.example.dockerversion.repository.PackRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PackService {
    private final PackRepository packRepository;
    private final CenterService centerService;
    private final SideService sideService;

    public GenericResponse<PackageDTO> createPackage(PackageCreateRequest request) {
        try {
            Package aPackage = Package.builder()
                    .center(centerService.getMainCenter())
                    .destinationAddress(request.getDestinationAddress())
                    .recipientName(request.getRecipientName())
                    .shippingStatus(Boolean.FALSE)
                    .isDelivered(Boolean.FALSE)
                    .build();
            packRepository.save(aPackage);
            PackageDTO packageDTO = new PackageDTO(
                    aPackage.getId(),
                    aPackage.getRecipientName(),
                    aPackage.getDestinationAddress(),
                    aPackage.getShippingStatus(),
                    aPackage.getIsDelivered(),
                    aPackage.getEstimatedArrivalTime()
            );
            return new GenericResponse<>(packageDTO, Boolean.TRUE);
        } catch (Exception e) {
            throw new PackageSavedException();
        }
    }

    public GenericResponse<PackageDTO> updatePackage(PackageUpdateRequest request) {
        try {
            Package aPackage = packRepository.findById(request.getId()).orElseThrow(PackageUpdateException::new);
            aPackage.setDestinationAddress(request.getDestinationAddress());
            aPackage.setRecipientName(request.getRecipientName());
            packRepository.save(aPackage);
            PackageDTO packageDTO = new PackageDTO(
                    aPackage.getId(),
                    aPackage.getRecipientName(),
                    aPackage.getDestinationAddress(),
                    aPackage.getShippingStatus(),
                    aPackage.getIsDelivered(),
                    aPackage.getEstimatedArrivalTime()
            );
            return new GenericResponse<>(packageDTO, Boolean.TRUE);
        } catch (Exception e) {
            throw new PackageUpdateException();
        }
    }

    public GenericResponse<List<PackageDTO>> readAllPackage() {
        try {
            List<PackageDTO> packageDTOList = packRepository.findAll().stream()
                    .map(packet -> new PackageDTO(
                            packet.getId(),
                            packet.getRecipientName(),
                            packet.getDestinationAddress(),
                            packet.getShippingStatus(),
                            packet.getIsDelivered(),
                            packet.getEstimatedArrivalTime()
                    )).collect(Collectors.toList());
            return new GenericResponse<>(
                    packageDTOList
                    , Boolean.TRUE
            );
        } catch (Exception e) {
            throw new PackageReadException();
        }
    }

    public GenericResponse<PackageDTO> readPackage(Long id) {
        try {
            Package aPackage = packRepository.findById(id).orElseThrow(PackageNotFoundException::new);
            PackageDTO packageDTO = new PackageDTO(
                    aPackage.getId(),
                    aPackage.getRecipientName(),
                    aPackage.getDestinationAddress(),
                    aPackage.getShippingStatus(),
                    aPackage.getIsDelivered(),
                    aPackage.getEstimatedArrivalTime()
            );
            return new GenericResponse<>(
                    packageDTO
                    , Boolean.TRUE
            );
        } catch (Exception e) {
            throw new PackageNotFoundException();
        }
    }

    public GenericResponse<GeneralResponse> deletePackage(Long id) {
        try {
            packRepository.deleteById(id);
            return new GenericResponse<>(GeneralResponse.builder().responseMessage("Packet deleted successfully").build(), Boolean.TRUE);
        } catch (Exception e) {
            throw new PackageDeleteException();
        }
    }

    public GenericResponse<GeneralResponse> deleteAllPackage() {
        try {
            packRepository.deleteAll();
            return new GenericResponse<>(GeneralResponse.builder().responseMessage("All packets deleted successfully").build(), Boolean.TRUE);
        } catch (Exception e) {
            throw new PackageDeleteException();
        }
    }

    protected Package getPackageById(Long id) {
        try {
            return packRepository.findById(id).orElseThrow(PackageNotFoundException::new);
        } catch (Exception e) {
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
            throw new PackageNotFoundException();
        }
    }

    protected void updatePackageStatus(Long packetId) {
        try {
            Package aPackage = packRepository.findById(packetId).orElseThrow(PackageNotFoundException::new);
            aPackage.setShippingStatus(Boolean.TRUE);
            aPackage.setDeliveryDate(new Date());
            savePackage(aPackage);
        } catch (Exception e) {
            throw new PackageUpdateException();
        }
    }

    private void savePackage(Package aPackage) {
        try {
            System.out.println(aPackage.getId());
            packRepository.save(aPackage);
        } catch (Exception e) {
            throw new PackageSavedException();
        }
    }

    protected void deletePackage(Package aPackage) {
        try {
            packRepository.delete(aPackage);
        } catch (Exception e) {
            throw new PackageDeleteException();
        }
    }

    protected void packageDelivered(Package aPackage) {
        try {
            aPackage.setIsDelivered(Boolean.TRUE);
            aPackage.setShippingStatus(Boolean.FALSE);
            aPackage.setDateDelivered(new Date());
            packRepository.save(aPackage);
        } catch (Exception e) {
            throw new PackageUpdateException();
        }
    }

    protected List<Package> getAllPackage() {
        try {
            return packRepository.findAll();
        } catch (Exception e) {
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
                    AddressCoordinate coordinate = sideService.findCoordinate(address);
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
                    return new GenericResponse<>(generalResponse, Boolean.TRUE);

                } else {
                    throw new PackageDeliveringStatusException();
                }
            }
        } catch (Exception e) {
            throw new PackageDeliveringStatusException();
        }
    }
}


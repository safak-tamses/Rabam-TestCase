package com.example.dockerversion.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class PackageDTO {
    private Long id;
    private String recipientName;
    private String destinationAddress;
    private Boolean shippingStatus;
    private Boolean isDelivered;
    private LocalDateTime estimatedArrivalTime;
}

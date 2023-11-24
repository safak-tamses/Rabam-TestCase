package com.example.rabam.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourierStatusDTO {
    private String name;
    private String surname;
    private String phone;
    private Date lastUpdatedTime;
    private Boolean hasItBeenDelivered;
    private Double currentLongitude;
    private Double currentLatitude;
    private Double currentSpeed;
}

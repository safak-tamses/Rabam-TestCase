package com.example.rabam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private Date createdDate;
    private Date updateDate;

    @OneToOne
    @JoinColumn(name = "package_id")
    private Package dispatchedPackage;

// courier destination info
    private Double currentLongitude;
    private Double currentLatitude;
    private Double currentSpeed;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    private Boolean hasItBeenDelivered;

}
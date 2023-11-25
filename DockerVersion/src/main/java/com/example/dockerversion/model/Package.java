package com.example.dockerversion.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipientName;
    private String destinationAddress;
    private Boolean shippingStatus;
    private Boolean isDelivered;
    private LocalDateTime estimatedArrivalTime;
    private Date deliveryDate;
    private Date dateDelivered;


    @OneToOne(mappedBy = "dispatchedPackage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Courier deliveryCourier;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

}

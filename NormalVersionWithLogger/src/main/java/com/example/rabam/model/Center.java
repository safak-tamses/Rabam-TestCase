package com.example.rabam.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "center")
public class Center {
    @Id
    private final Long id = 1L;
    private final String centerAddress = "Yenibosna Merkez, SER Plaza, Basın Ekspres Yolu, Kavak Sk., 34530 İstanbul";
    private final Double longitude = 28.814281;
    private final Double latitude = 41.013176;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<Courier> courierList;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<Package> packageList;

}



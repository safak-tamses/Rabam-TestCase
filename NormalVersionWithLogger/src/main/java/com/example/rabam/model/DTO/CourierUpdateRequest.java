package com.example.rabam.model.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CourierUpdateRequest{
    private Long courierId;
    private String name;
    private String surname;
    private String phone;
}

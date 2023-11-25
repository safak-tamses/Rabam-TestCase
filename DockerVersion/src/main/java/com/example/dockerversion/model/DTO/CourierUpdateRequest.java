package com.example.dockerversion.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

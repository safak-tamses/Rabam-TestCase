package com.example.dockerversion.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class CourierCreateRequest {
    private String name;
    private String surname;
    private String phone;
}

package com.example.dockerversion.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class CourierDTO {
    private String name;
    private String surname;
    private String phone;
    private Date createdDate;
    private Date updateDate;
}

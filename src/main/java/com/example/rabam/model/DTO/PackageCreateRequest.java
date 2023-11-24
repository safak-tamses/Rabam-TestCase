package com.example.rabam.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PackageCreateRequest {
    private String recipientName;
    private String destinationAddress;
}

package com.example.rabam.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PackageUpdateRequest {
    private Long id;
    private String recipientName;
    private String destinationAddress;
}

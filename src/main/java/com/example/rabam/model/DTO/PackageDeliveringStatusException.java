package com.example.rabam.model.DTO;

public class PackageDeliveringStatusException extends RuntimeException{
    public PackageDeliveringStatusException() {
        super("Package estimate time error");
    }
}

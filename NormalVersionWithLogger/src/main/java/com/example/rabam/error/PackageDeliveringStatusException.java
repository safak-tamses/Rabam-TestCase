package com.example.rabam.error;

public class PackageDeliveringStatusException extends RuntimeException{
    public PackageDeliveringStatusException() {
        super("Package estimate time error");
    }
}

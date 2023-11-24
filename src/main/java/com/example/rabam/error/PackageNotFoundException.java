package com.example.rabam.error;

public class PackageNotFoundException extends RuntimeException{
    public PackageNotFoundException() {
        super("Packet not found!");
    }
}

package com.example.dockerversion.error;

public class PackageNotFoundException extends RuntimeException{
    public PackageNotFoundException() {
        super("Packet not found!");
    }
}

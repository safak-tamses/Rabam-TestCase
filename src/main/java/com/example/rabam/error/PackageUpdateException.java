package com.example.rabam.error;

public class PackageUpdateException extends RuntimeException{
    public PackageUpdateException() {
        super("Packet didn't update");
    }
}

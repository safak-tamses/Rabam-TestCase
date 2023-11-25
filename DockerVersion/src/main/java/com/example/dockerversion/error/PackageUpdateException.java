package com.example.dockerversion.error;

public class PackageUpdateException extends RuntimeException{
    public PackageUpdateException() {
        super("Packet didn't update");
    }
}

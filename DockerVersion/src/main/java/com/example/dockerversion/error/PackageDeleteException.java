package com.example.dockerversion.error;

public class PackageDeleteException extends RuntimeException{
    public PackageDeleteException() {
        super("Packet didn't delete");
    }
}

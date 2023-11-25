package com.example.dockerversion.error;

public class PackageReadException extends RuntimeException{
    public PackageReadException() {
        super("Packet didn't read!");
    }
}
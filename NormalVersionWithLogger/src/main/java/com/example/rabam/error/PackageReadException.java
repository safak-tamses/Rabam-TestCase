package com.example.rabam.error;

public class PackageReadException extends RuntimeException{
    public PackageReadException() {
        super("Packet didn't read!");
    }
}

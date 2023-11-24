package com.example.rabam.error;

public class PackageSavedException extends RuntimeException{
    public PackageSavedException() {
        super("Packet didn't save!");
    }
}

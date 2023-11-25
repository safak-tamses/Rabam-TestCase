package com.example.dockerversion.error;

public class PackageSavedException extends RuntimeException{
    public PackageSavedException() {
        super("Packet didn't save!");
    }
}


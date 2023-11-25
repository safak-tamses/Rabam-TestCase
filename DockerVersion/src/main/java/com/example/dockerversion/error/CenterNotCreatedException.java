package com.example.dockerversion.error;

public class CenterNotCreatedException extends RuntimeException{
    public CenterNotCreatedException() {
        super("Center not created!");
    }
}

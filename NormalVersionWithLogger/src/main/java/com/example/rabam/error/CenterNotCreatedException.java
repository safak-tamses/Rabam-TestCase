package com.example.rabam.error;

public class CenterNotCreatedException extends RuntimeException{
    public CenterNotCreatedException() {
        super("Center not created!");
    }
}

package com.example.dockerversion.error;

public class CourierSavedException extends RuntimeException{
    public CourierSavedException() {
        super("Courier didn't save!");
    }
}

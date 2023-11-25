package com.example.dockerversion.error;

public class CourierNotFoundException extends RuntimeException{
    public CourierNotFoundException() {
        super("Courier not found!");
    }
}

package com.example.rabam.error;

public class CourierNotFoundException extends RuntimeException{
    public CourierNotFoundException() {
        super("Courier not found!");
    }
}

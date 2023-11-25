package com.example.rabam.error;

public class CourierNotAvailableException extends RuntimeException{
    public CourierNotAvailableException() {
        super("Courier haa a deliver. Not available!");
    }
}

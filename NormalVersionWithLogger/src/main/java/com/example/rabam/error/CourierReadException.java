package com.example.rabam.error;

public class CourierReadException extends RuntimeException{
    public CourierReadException() {
        super("Courier didn't read!");
    }
}

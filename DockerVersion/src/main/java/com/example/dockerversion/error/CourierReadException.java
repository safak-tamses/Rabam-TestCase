package com.example.dockerversion.error;

public class CourierReadException extends RuntimeException{
    public CourierReadException() {
        super("Courier didn't read!");
    }
}

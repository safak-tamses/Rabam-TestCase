package com.example.dockerversion.error;

public class CourierUpdateException extends RuntimeException{
    public CourierUpdateException() {
        super("Courier didn't update");
    }
}

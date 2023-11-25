package com.example.dockerversion.error;

public class CourierDeleteException extends RuntimeException{
    public CourierDeleteException() {
        super("Courier didn't deleted!");
    }
}

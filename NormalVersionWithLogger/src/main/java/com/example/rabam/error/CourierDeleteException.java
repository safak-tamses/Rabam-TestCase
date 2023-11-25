package com.example.rabam.error;

public class CourierDeleteException extends RuntimeException{
    public CourierDeleteException() {
        super("Courier didn't deleted!");
    }
}

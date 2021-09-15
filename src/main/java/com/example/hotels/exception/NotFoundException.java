package com.example.hotels.exception;

public class NotFoundException extends RuntimeException{

    private int id;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message,int id) {
        super(message);
        this.id = id;
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
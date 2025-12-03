package com.kay.driving_exam_app.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    String message;
    public ResourceNotFoundException(){}
    public ResourceNotFoundException(String message){
        super(message);
        this.message = message;
    }


}

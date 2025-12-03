package com.kay.driving_exam_app.exceptions;

public class ResourceAvailableException extends RuntimeException {
    String questionText;


    public ResourceAvailableException(String questionText) {
        super(String.format("%s:  exist", questionText));
        this.questionText = questionText;
    }

    public ResourceAvailableException() {
    }
}

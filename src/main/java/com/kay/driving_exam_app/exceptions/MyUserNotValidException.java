package com.kay.driving_exam_app.exceptions;

public class MyUserNotValidException extends RuntimeException{
     String email;
     String password;

    public MyUserNotValidException(String email, String password)
    {
        super(String.format("Wrong Username: %s or Password: %s", email, password));
        this.email = email;
        this.password = password;
    }

    public MyUserNotValidException() {
    }
}

package com.kay.driving_exam_app.controller;


import com.kay.driving_exam_app.dto.SignInRequest;
import com.kay.driving_exam_app.dto.SignUpRequest;
import com.kay.driving_exam_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/usersignup")
    public ResponseEntity<String> userSignUp(@RequestBody SignUpRequest user)
    {
        return userService.UserSignUpSave(user);
    }

    @PostMapping("/usersignin")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest credentials){
        return  userService.signIn(credentials);
    }


}

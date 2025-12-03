package com.kay.driving_exam_app.controller;


import com.kay.driving_exam_app.dto.SignInRequest;
import com.kay.driving_exam_app.dto.SignUpRequest;
import com.kay.driving_exam_app.dto.UserInfoResponse;
import com.kay.driving_exam_app.exceptions.ResourceNotFoundException;
import com.kay.driving_exam_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@Valid @RequestBody SignUpRequest user)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveSignUp(user));
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest credentials){
        UserInfoResponse infoResponse = userService.signIn(credentials);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                infoResponse.getResponseCookie().toString()).body(infoResponse);

    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(){
        ResponseCookie cookie = userService.signOutUser();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                cookie.toString()).body("You've Been Logged Out");
    }


}

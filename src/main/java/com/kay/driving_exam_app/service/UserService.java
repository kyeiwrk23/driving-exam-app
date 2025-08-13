package com.kay.driving_exam_app.service;

import com.kay.driving_exam_app.dto.SignInRequest;
import com.kay.driving_exam_app.dto.SignUpRequest;
import com.kay.driving_exam_app.model.Role;
import com.kay.driving_exam_app.model.User;
import com.kay.driving_exam_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    final Pattern VALID_EMAIL_REGEX =  Pattern.compile( "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",Pattern.CASE_INSENSITIVE);


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);



    public ResponseEntity<String> UserSignUpSave(SignUpRequest signUpUser) {


        User newUser = new User();
        if( userRepository.findByEmail(signUpUser.getEmail())==null) {
            Matcher matcher = VALID_EMAIL_REGEX.matcher(signUpUser.getEmail());
            if(matcher.matches()) {
                newUser.setFirstName(signUpUser.getFirstName());
                newUser.setLastName(signUpUser.getLastName());
                newUser.setEmail(signUpUser.getEmail().toLowerCase(Locale.ROOT));
                newUser.setPassword(encoder.encode(signUpUser.getPassword()));
                newUser.setCreatedAt(LocalDateTime.now());
                newUser.setUpdatedAt(LocalDateTime.now());
                newUser.setRole(Role.ROLE_USER);
                userRepository.save(newUser);
                return ResponseEntity.ok().body(jwtService.generateToken(signUpUser.getEmail()));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Enter Correct Email Address");
            }
        }else
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("User Already Exist");
        }



    public ResponseEntity<String> signIn(SignInRequest credentials){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword()));
        if(authentication.isAuthenticated())
            return ResponseEntity.ok().body(jwtService.generateToken(credentials.getEmail()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login Failed");


    }

    public ResponseEntity<String> adminSignUpSave(SignUpRequest adminUser) {
        var admin = new User();
       if(userRepository.findByEmail(adminUser.getEmail()) !=null) {
           Matcher matcher = VALID_EMAIL_REGEX.matcher(adminUser.getEmail());
           if (matcher.matches()) {
               admin.setFirstName(adminUser.getFirstName());
               admin.setLastName(adminUser.getLastName());
               admin.setEmail(adminUser.getEmail().toLowerCase(Locale.ROOT));
               admin.setPassword(encoder.encode(adminUser.getPassword()));
               admin.setRole(Role.ROLE_ADMIN);
               userRepository.save(admin);
               return ResponseEntity.ok().body(jwtService.generateToken(adminUser.getEmail()));
           }
           else{
               return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Enter Correct Email Address");
           }
       }else
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Admin Already Exist");
    }
}

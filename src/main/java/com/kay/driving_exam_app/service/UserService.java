package com.kay.driving_exam_app.service;

import com.kay.driving_exam_app.config.JwtUtils;
import com.kay.driving_exam_app.dto.SignInRequest;
import com.kay.driving_exam_app.dto.SignUpRequest;
import com.kay.driving_exam_app.dto.UserInfoResponse;
import com.kay.driving_exam_app.exceptions.MyUserNotValidException;
import com.kay.driving_exam_app.exceptions.ResourceAvailableException;
import com.kay.driving_exam_app.exceptions.ResourceNotFoundException;
import com.kay.driving_exam_app.model.Role;
import com.kay.driving_exam_app.model.RoleImpl;
import com.kay.driving_exam_app.model.User;
import com.kay.driving_exam_app.model.UserPrincipal;
import com.kay.driving_exam_app.repository.RoleRepository;
import com.kay.driving_exam_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;




    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    @Autowired
    private JwtUtils jwtUtils;


    public String saveSignUp(SignUpRequest signUpUser) {
        if(userRepository.existsUserByEmail(signUpUser.getEmail())){
            throw new ResourceAvailableException(signUpUser.getEmail());
        }


        User newUser = new User();
        newUser.setFirstName(signUpUser.getFirstName());
        newUser.setLastName(signUpUser.getLastName());
        newUser.setEmail(signUpUser.getEmail().toLowerCase(Locale.ROOT));
        newUser.setPassword(encoder.encode(signUpUser.getPassword()));
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        Set<String> roleTypes = signUpUser.getRole();
        Set<Role> role = new HashSet<>();
        for (String roleType : roleTypes) {
            logger.debug("Role: {}", roleType);
        }



        if (roleTypes.isEmpty()) {
            Role userRole = roleRepository.findByRoleName(RoleImpl.ROLE_USER)
                    .orElseThrow(()-> new ResourceNotFoundException("Error: Role Not Found "));
            role.add(userRole);
        }else{
            roleTypes.forEach(roles->{
                switch (roles) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(RoleImpl.ROLE_ADMIN)
                                .orElseThrow(()-> new ResourceNotFoundException("Error: Role Not Found "));
                        role.add(adminRole);
                        break;
                    case "instructor":
                            Role instructorRole = roleRepository.findByRoleName(RoleImpl.ROLE_USER)
                                    .orElseThrow(()-> new ResourceNotFoundException("Error: Role Not Found "));
                            role.add(instructorRole);
                            break;
                    default:
                        Role userRole = roleRepository.findByRoleName(RoleImpl.ROLE_INSTRUCTOR)
                                .orElseThrow(()-> new ResourceNotFoundException("Error: Role Not Found "));
                        role.add(userRole);
                }
            });
        }

        newUser.setRoles(role);
        userRepository.save(newUser);
            return "Successfully Signed Up";
        }



    public UserInfoResponse signIn(SignInRequest credentials){
        Authentication authentication;
                try{
                    authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword()));

                }catch (MyUserNotValidException e){
                    throw new MyUserNotValidException(credentials.getEmail(), credentials.getPassword());
                }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        ResponseCookie jwtToken = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new UserInfoResponse(
                userDetails.getUserId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getEmail(),
                jwtToken,
                roles
        );
    }

    public ResponseCookie signOutUser(){
        return jwtUtils.getCleanJwtCookie();
    }

}

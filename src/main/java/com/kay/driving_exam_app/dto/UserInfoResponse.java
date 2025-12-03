package com.kay.driving_exam_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private ResponseCookie responseCookie;
    private List<String> roles;


}

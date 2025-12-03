package com.kay.driving_exam_app.dto;

import com.kay.driving_exam_app.model.RoleImpl;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @Email(message = "Format user@gmail.com")
    @NotNull(message = "Email cannot be Null")
    private String email;
    @NotNull(message = "Password cannot be Null")
    @Size(min = 6,max = 16,message = "Password between 6 and 16 characters")
    private String password;

}

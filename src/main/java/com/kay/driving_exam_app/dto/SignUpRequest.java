package com.kay.driving_exam_app.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotNull(message = "First name cannot be Null")
    private String firstName;
    @NotNull(message = "Last name cannot be Null")
    private String lastName;
    @Email(message = "Format user@gmail.com")
    @NotNull(message = "Email cannot be cannot be Null")
    private String email;
    @NotNull(message = "Password can't be Null")
    @Size(min = 6,max = 16,message = "Password must be between 6 to 16 charaters")
    private String password;
    @NotNull(message = "Role cannot be Null")
    private Set<String> role;
}

package com.kay.driving_exam_app.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @NotNull(message = "Id cannot be null")
    private Long id;
    private String answer;
}

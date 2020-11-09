package net.ellections.requests;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email field must be present.")
    private String email;

    @NotBlank(message = "Password field must be present.")
    private String password;
}
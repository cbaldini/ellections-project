package net.ellections.requests;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name field must be present.")
    private String name;
    private String password;

    @NotBlank(message = "Email field must be present.")
    private String email;

    @NotBlank(message = "TimeZone field must be present.")
    private String timezone;


}


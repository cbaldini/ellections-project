package net.ellections.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordUpdateRequest {

    @NotBlank(message = "Old Password field must be present.")
    @JsonProperty("old_password")
    private String oldPassword;

    @NotBlank(message = "New Password field must be present.")
    @JsonProperty("new_password")
    private String newPassword;
}

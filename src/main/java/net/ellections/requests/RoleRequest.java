package net.ellections.requests;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequest {

    @NotBlank(message = "Name field must be present.")
    private String name;
}
package net.ellections.requests;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import net.ellections.entities.Role;

@Data
@Builder
public class UserRequest {
    private Long id;
    private UUID uuid;
    @NotBlank(message = "Name field must be present.")
    private String name;
    private String password;
    private List<Role> roles;

    @NotBlank(message = "Email field must be present.")
    private String email;

}

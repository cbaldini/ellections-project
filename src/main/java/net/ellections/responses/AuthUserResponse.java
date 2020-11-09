package net.ellections.responses;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import net.ellections.entities.Role;

@Data
@Builder
public class AuthUserResponse {
    private UUID uuid;
    private String name;
    private String email;
    private String timezone;
    private List<Role> roles;
    private String token;
}

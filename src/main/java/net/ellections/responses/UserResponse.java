package net.ellections.responses;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import net.ellections.entities.Role;
import net.ellections.entities.User;

@Data
@Builder
public class UserResponse {

    private UUID uuid;
    private String name;
    private String email;
    private String timezone;
    private List<Role> roles;

    public static UserResponse fromEntity(User user){
        return UserResponse.builder()
            .uuid(user.getUuid())
            .name(user.getName())
            .email(user.getEmail())
            .timezone(user.getTimezone())
            .roles(user.getRoles())
            .build();
    }
}

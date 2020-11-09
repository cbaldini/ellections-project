package net.ellections.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.ellections.responses.AuthUserResponse;
import net.ellections.responses.UserResponse;

@Data
@Entity
@Table(name = "users")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String name;
    private String email;
    private String password;
    private String timezone;
    private Date lastLogin;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public AuthUserResponse toAuthResponse(String token) {
        return AuthUserResponse.builder()
            .token(token)
            .uuid(this.uuid)
            .name(this.name)
            .email(this.email)
            .timezone(this.timezone)
            .roles(this.roles)
            .build();
    }

    public UserResponse toResponse() {
        return UserResponse.builder()
            .uuid(this.uuid)
            .name(this.name)
            .email(this.email)
            .timezone(this.timezone)
            .roles(this.roles)
            .build();
    }
}


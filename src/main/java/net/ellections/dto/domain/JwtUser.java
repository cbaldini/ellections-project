package net.ellections.dto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import net.ellections.entities.Role;

@Data
@Builder
public class JwtUser {

    private String subject;
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Date issuedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Date expiration;

    private List<Role> roles;
}



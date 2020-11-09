package net.ellections.requests;

import lombok.Builder;
import lombok.Data;
import net.ellections.entities.User;

@Data
@Builder
public class CandidateRequest {
    private Long id;
    private User user;
    private String firstName;
    private String lastName;
    private String cuit;

}

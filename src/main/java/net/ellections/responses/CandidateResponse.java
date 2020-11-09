package net.ellections.responses;

import lombok.Builder;
import lombok.Data;
import net.ellections.entities.Candidate;

@Data
@Builder
public class CandidateResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String cuit;

    public static CandidateResponse fromEntity(Candidate candidate){
        return CandidateResponse.builder()
            .id(candidate.getId())
            .firstName(candidate.getFirstName())
            .lastName(candidate.getLastName())
            .cuit(candidate.getCuit())
            .build();
    }
}

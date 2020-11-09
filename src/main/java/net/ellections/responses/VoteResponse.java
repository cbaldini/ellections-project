package net.ellections.responses;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import net.ellections.entities.Area;
import net.ellections.entities.Vote;

@Data
@Builder
public class VoteResponse {

    private Long id;
    private Area area;
    private String elector_username;
    private String elected_employee;
    private String comment;
    private Date date;

    public static VoteResponse fromEntity(Vote vote){
        return VoteResponse.builder()
            .id(vote.getId())
            .area(vote.getArea())
            .elector_username(vote.getUser().getName())
            .elected_employee(vote.getCandidate().getFirstName() + " " + vote.getCandidate().getLastName())
            .comment(vote.getComment())
            .date(vote.getDate())
            .build();
    }
}

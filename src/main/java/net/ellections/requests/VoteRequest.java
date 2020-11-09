package net.ellections.requests;

import java.util.Date;
import lombok.Data;
import net.ellections.entities.Area;
import net.ellections.entities.Candidate;
import net.ellections.entities.User;

@Data
public class VoteRequest {

    private Area area;
    private User user;
    private Candidate candidate;
    private String comment;
    private Date month;
}

package net.ellections.controllers;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.ellections.entities.Vote;
import net.ellections.exceptions.ForbiddenException;
import net.ellections.exceptions.UnauthorizedException;
import net.ellections.requests.VoteRequest;
import net.ellections.responses.VoteResponse;
import net.ellections.services.UserService;
import net.ellections.services.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<VoteResponse>> getVotes (
        @RequestHeader(name = "Authorization") String token) {
            if (!userService.verifyRole(token, "ADMIN")) {
                throw new ForbiddenException();
            }
        return ResponseEntity.ok(voteService.getVotes());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Vote> createVote(
        @RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody VoteRequest request) {
        if (!userService.verifyRole(token, "ADMIN") && (!userService.verifyRole(token, "EMPLOYEE"))) {
            throw new UnauthorizedException();
        }
        System.out.println("ID DE USUARIO QUE VOTA: " + (userService.verifyId(token)).toString() + "########################################");
        System.out.println("USUARIO VOTADO: " + voteService.findVoteByUserCandidateAndArea(
            request.getCandidate().getId(),
            userService.verifyId(token),
            request.getArea().getId()
        ).toString());
        if((voteService.findVoteByUserCandidateAndArea(
            request.getCandidate().getId(),
            userService.verifyId(token),
            request.getArea().getId())).isPresent()){
            throw new UnauthorizedException();
        }
        else{return ResponseEntity.ok(voteService.createVote(request));}

    }
}

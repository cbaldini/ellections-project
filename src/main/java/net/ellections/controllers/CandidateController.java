package net.ellections.controllers;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.ellections.entities.Candidate;
import net.ellections.responses.CandidateResponse;
import net.ellections.services.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Candidate>> getAllCandidates(){
        return ResponseEntity.ok(candidateService.getCandidates());
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<CandidateResponse> createCandidate(@RequestBody @Valid Candidate candidate){
        return ResponseEntity.ok(candidateService.createCandidate(candidate));
    }

}

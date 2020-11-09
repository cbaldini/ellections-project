package net.ellections.services;

import java.util.List;
import lombok.AllArgsConstructor;
import net.ellections.entities.Candidate;
import net.ellections.reporitories.CandidateRepository;
import net.ellections.responses.CandidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CandidateService {

    @Autowired
    private final CandidateRepository candidateRepository;

    public List<Candidate> getCandidates(){

        return candidateRepository.findAll();
    }

    public CandidateResponse createCandidate(Candidate candidate){

        Candidate saved = candidateRepository.save(candidate);
        return CandidateResponse.fromEntity(saved);
    }
}

package net.ellections.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import net.ellections.entities.Vote;
import net.ellections.reporitories.VoteRepository;
import net.ellections.requests.VoteRequest;
import net.ellections.responses.VoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {

    @Autowired
    private final VoteRepository voteRepository;

    public List<VoteResponse> getVotes() {

        return voteRepository.findAll()
            .stream().map(VoteResponse::fromEntity).collect(Collectors.toList());
    }
    public Vote createVote(VoteRequest request){

        Vote vote = Vote.builder()
            .comment(request.getComment())
            .date(request.getMonth())
            .area(request.getArea())
            .candidate(request.getCandidate())
            .user(request.getUser())
            .build();

        return voteRepository.save(vote);
    }

    public Optional<Vote> findVoteByUserCandidateAndArea(Long candidateId, Long userId, Long areaId){
        return voteRepository.findByUserCandidateAndArea(candidateId, userId, areaId);
    }

}
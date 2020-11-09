package net.ellections.reporitories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import net.ellections.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>, Serializable, JpaSpecificationExecutor<Vote> {

    List<Vote> findAll();

    @Query (value = "SELECT * FROM votes WHERE user_id = ?1 AND candidate_id = ?2 AND area_id = ?3", nativeQuery = true)
    Optional <Vote> findByUserCandidateAndArea(Long userId, Long candidateId, Long areaId);

}

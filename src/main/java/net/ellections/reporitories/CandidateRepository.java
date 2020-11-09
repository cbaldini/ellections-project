package net.ellections.reporitories;

import java.io.Serializable;
import java.util.List;
import net.ellections.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository <Candidate, Long> , Serializable, JpaSpecificationExecutor<Candidate> {
    List<Candidate> findAll();
}

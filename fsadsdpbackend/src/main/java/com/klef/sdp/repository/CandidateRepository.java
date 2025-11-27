package com.klef.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.sdp.model.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    
    // Find a Candidate by username and password
    public Candidate findByUsernameAndPassword(String username, String password);

    // Get the total count of Candidates
    @Query("SELECT COUNT(c) FROM Candidate c")
    public long countCandidates();
}

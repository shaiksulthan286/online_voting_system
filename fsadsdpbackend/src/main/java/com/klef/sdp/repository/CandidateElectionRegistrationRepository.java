package com.klef.sdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.sdp.model.CandidateElectionRegistration;
import com.klef.sdp.model.RegistrationStatus;

@Repository
public interface CandidateElectionRegistrationRepository
        extends JpaRepository<CandidateElectionRegistration, Long> {

    Optional<CandidateElectionRegistration> findByCandidateIdAndElectionId(Integer candidateId, Integer electionId);

    List<CandidateElectionRegistration> findByCandidateId(Integer candidateId);

    List<CandidateElectionRegistration> findByStatus(RegistrationStatus status);

    List<CandidateElectionRegistration> findByElectionId(Integer electionId);

    List<CandidateElectionRegistration> findByElectionIdAndStatus(Integer electionId, RegistrationStatus status);
}


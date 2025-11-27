package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.model.CandidateElectionRegistration;
import com.klef.sdp.model.RegistrationStatus;

public interface CandidateElectionRegistrationService {

    CandidateElectionRegistration createRegistration(Integer candidateId, Integer electionId);

    List<CandidateElectionRegistration> getRegistrationsForCandidate(Integer candidateId);

    List<CandidateElectionRegistration> getRegistrationsByStatus(RegistrationStatus status);

    List<CandidateElectionRegistration> getRegistrationsForElection(Integer electionId, RegistrationStatus status);

    List<CandidateElectionRegistration> getAllRegistrations();

    CandidateElectionRegistration updateRegistrationStatus(Long registrationId, RegistrationStatus status);
}


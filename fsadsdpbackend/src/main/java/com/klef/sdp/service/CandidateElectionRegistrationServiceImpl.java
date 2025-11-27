package com.klef.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.klef.sdp.model.Candidate;
import com.klef.sdp.model.CandidateElectionRegistration;
import com.klef.sdp.model.Election;
import com.klef.sdp.model.RegistrationStatus;
import com.klef.sdp.repository.CandidateElectionRegistrationRepository;
import com.klef.sdp.repository.CandidateRepository;
import com.klef.sdp.repository.ElectionRepository;

@Service
public class CandidateElectionRegistrationServiceImpl implements CandidateElectionRegistrationService {

    @Autowired
    private CandidateElectionRegistrationRepository registrationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Override
    @Transactional
    public CandidateElectionRegistration createRegistration(Integer candidateId, Integer electionId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found for id: " + candidateId));

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new IllegalArgumentException("Election not found for id: " + electionId));

        return registrationRepository.findByCandidateIdAndElectionId(candidateId, electionId)
                .map(existing -> {
                    if (existing.getStatus() == RegistrationStatus.REJECTED) {
                        existing.setStatus(RegistrationStatus.PENDING);
                        return registrationRepository.save(existing);
                    }
                    throw new IllegalStateException("Registration already exists with status: " + existing.getStatus());
                })
                .orElseGet(() -> {
                    CandidateElectionRegistration registration = new CandidateElectionRegistration();
                    registration.setCandidate(candidate);
                    registration.setElection(election);
                    registration.setStatus(RegistrationStatus.PENDING);
                    return registrationRepository.save(registration);
                });
    }

    @Override
    public List<CandidateElectionRegistration> getRegistrationsForCandidate(Integer candidateId) {
        return registrationRepository.findByCandidateId(candidateId);
    }

    @Override
    public List<CandidateElectionRegistration> getRegistrationsByStatus(RegistrationStatus status) {
        return registrationRepository.findByStatus(status);
    }

    @Override
    public List<CandidateElectionRegistration> getRegistrationsForElection(Integer electionId, RegistrationStatus status) {
        if (status == null) {
            return registrationRepository.findByElectionId(electionId);
        }
        return registrationRepository.findByElectionIdAndStatus(electionId, status);
    }

    @Override
    public List<CandidateElectionRegistration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Override
    @Transactional
    public CandidateElectionRegistration updateRegistrationStatus(Long registrationId, RegistrationStatus status) {
        CandidateElectionRegistration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found for id: " + registrationId));
        registration.setStatus(status);
        return registrationRepository.save(registration);
    }
}


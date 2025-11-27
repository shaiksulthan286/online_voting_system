package com.klef.sdp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Candidate;
import com.klef.sdp.repository.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public String candidateRegistration(Candidate candidate) {
        candidateRepository.save(candidate);
        return "Candidate Registered Successfully";
    }

    @Override
    public Candidate checkCandidateLogin(String username, String password) {
        return candidateRepository.findByUsernameAndPassword(username, password);
    }
    
    @Override
    public String updateCandidateProfile(Candidate candidate) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(candidate.getId());

        String message;

        if (optionalCandidate.isPresent()) {
            Candidate existingCandidate = optionalCandidate.get();

            existingCandidate.setName(candidate.getName());
            existingCandidate.setGender(candidate.getGender());
            existingCandidate.setDob(candidate.getDob());
            existingCandidate.setEmail(candidate.getEmail());
            existingCandidate.setUsername(candidate.getUsername());
            existingCandidate.setPassword(candidate.getPassword());
            existingCandidate.setMobileno(candidate.getMobileno());
            existingCandidate.setParty(candidate.getParty());
            existingCandidate.setArea(candidate.getArea());

            candidateRepository.save(existingCandidate); // Save the updated object

            message = "Candidate profile updated successfully.";
        } else {
            message = "Candidate ID not found. Update failed.";
        }

        return message;
    }

    @Override
    public Candidate getCandidateById(int cid) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(cid);
        return optionalCandidate.orElse(null);
    }

    @Override
    public String registerCandidate(Candidate candidate) {
        return candidateRegistration(candidate);
    }
}

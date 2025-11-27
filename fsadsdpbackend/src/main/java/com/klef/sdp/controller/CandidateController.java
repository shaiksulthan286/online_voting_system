package com.klef.sdp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.dto.CandidateElectionRegistrationDTO;
import com.klef.sdp.dto.CandidateElectionRegistrationRequest;
import com.klef.sdp.model.Candidate;
import com.klef.sdp.model.CandidateElectionRegistration;
import com.klef.sdp.service.CandidateElectionRegistrationService;
import com.klef.sdp.service.CandidateService;

@RestController
@RequestMapping("/candidate")
@CrossOrigin("*")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateElectionRegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<String> registerCandidate(@RequestBody Candidate candidate) {
        try {
            return ResponseEntity.ok(candidateService.registerCandidate(candidate));
        } catch (Exception e) {
            e.printStackTrace();  // log the real issue
            return ResponseEntity.status(500).body("Candidate Registration Failed: " + e.getMessage());
        }

    }

    @PostMapping("/checkcandidatelogin")
    public ResponseEntity<?> checkCandidateLogin(@RequestBody Candidate candidate) {
        try {
            Candidate c = candidateService.checkCandidateLogin(candidate.getUsername(), candidate.getPassword());
            if (c != null) return ResponseEntity.ok(c);
            else return ResponseEntity.status(401).body("Invalid Username or Password.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    @PutMapping("/updateprofile")
    public ResponseEntity<String> updateCandidateProfile(@RequestBody Candidate candidate) {
        try {
            return ResponseEntity.ok(candidateService.updateCandidateProfile(candidate));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update Candidate profile.");
        }
    }

    @PostMapping("/election/register")
    public ResponseEntity<?> registerForElection(@RequestBody CandidateElectionRegistrationRequest request) {
        try {
            CandidateElectionRegistration registration = registrationService
                    .createRegistration(request.getCandidateId(), request.getElectionId());
            return ResponseEntity.ok(CandidateElectionRegistrationDTO.fromEntity(registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to register for election: " + e.getMessage());
        }
    }

    @GetMapping("/{candidateId}/election-registrations")
    public ResponseEntity<?> getCandidateRegistrations(@PathVariable Integer candidateId) {
        try {
            List<CandidateElectionRegistration> registrations = registrationService
                    .getRegistrationsForCandidate(candidateId);
            List<CandidateElectionRegistrationDTO> response = registrations.stream()
                    .map(CandidateElectionRegistrationDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch election registrations: " + e.getMessage());
        }
    }
}

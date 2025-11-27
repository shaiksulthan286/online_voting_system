package com.klef.sdp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.sdp.model.Election;
import com.klef.sdp.model.Voter;
import com.klef.sdp.dto.CandidateSummaryDTO;
import com.klef.sdp.dto.CastVoteRequest;
import com.klef.sdp.dto.ElectionWithCandidatesDTO;
import com.klef.sdp.model.CandidateElectionRegistration;
import com.klef.sdp.model.RegistrationStatus;
import com.klef.sdp.service.VoterService;
import com.klef.sdp.service.CandidateElectionRegistrationService;
import com.klef.sdp.service.VoteService;

@RestController
@RequestMapping("/voter")
@CrossOrigin("*")
public class VoterController 
{
    @Autowired
    private VoterService voterService;

    @Autowired
    private CandidateElectionRegistrationService registrationService;

    @Autowired
    private VoteService voteService;

    @PostMapping("/registration")
    public ResponseEntity<String> voterRegistration(@RequestBody Voter voter)
    {
        try {
            String output = voterService.voterregistration(voter);
            return ResponseEntity.ok(output);
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Voter Registration Failed ...");
        }
    }

    @PostMapping("/checkvoterlogin")
    public ResponseEntity<?> checkvoterLogin(@RequestBody Voter voter) 
    {
        try {
            Voter v = voterService.checkvoterlogin(voter.getUsername(), voter.getPassword());
            if (v != null) {
                return ResponseEntity.ok(v);
            } else {
                return ResponseEntity.status(401).body("Invalid Username or Password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    @PutMapping("/updatevoterprofile")
    public ResponseEntity<String> updatevoterprofile(@RequestBody Voter voter) {
        try {
            String output = voterService.updatevoterprofile(voter);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update Voter profile.");
        }
    }

    // ✅ New Endpoint: Get Total Voter Count
    @GetMapping("/count")
    public ResponseEntity<Long> getVoterCount() {
        try {
            long count = voterService.getVoterCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(0L);
        }
    }
    
    @GetMapping("/viewallelections")
    public ResponseEntity<List<Election>> viewAllElections() {
        try {
            List<Election> elections = voterService.viewAllElections();
            if (elections == null || elections.isEmpty()) {
                return ResponseEntity.ok(List.of()); // return empty list safely
            }
            return ResponseEntity.ok(elections);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/elections/with-candidates")
    public ResponseEntity<?> getElectionsWithApprovedCandidates() {
        try {
            List<Election> elections = voterService.viewAllElections();
            List<ElectionWithCandidatesDTO> response = elections.stream()
                    .map(election -> {
                        List<CandidateElectionRegistration> approvedRegistrations = registrationService
                                .getRegistrationsForElection(election.getId(), RegistrationStatus.APPROVED);
                        return ElectionWithCandidatesDTO.fromEntities(election, approvedRegistrations);
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load elections: " + e.getMessage());
        }
    }

    @GetMapping("/elections/{electionId}/candidates")
    public ResponseEntity<?> getApprovedCandidatesForElection(@PathVariable Integer electionId) {
        try {
            List<CandidateElectionRegistration> registrations = registrationService
                    .getRegistrationsForElection(electionId, RegistrationStatus.APPROVED);
            List<CandidateSummaryDTO> response = registrations.stream()
                    .map(CandidateSummaryDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load candidates: " + e.getMessage());
        }
    }

    @PostMapping("/vote")
    public ResponseEntity<?> castVote(@RequestBody CastVoteRequest request) {
        try {
            voteService.castVote(request.getVoterId(), request.getElectionId(), request.getCandidateId());
            return ResponseEntity.ok("Vote submitted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to submit your vote: " + e.getMessage());
        }
    }
}

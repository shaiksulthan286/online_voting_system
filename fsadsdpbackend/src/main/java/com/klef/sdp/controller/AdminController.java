package com.klef.sdp.controller;

import com.klef.sdp.dto.CandidateElectionRegistrationDTO;
import com.klef.sdp.dto.ElectionDTO;
import com.klef.sdp.dto.UpdateRegistrationStatusRequest;
import com.klef.sdp.model.Admin;
import com.klef.sdp.model.Candidate;
import com.klef.sdp.model.CandidateElectionRegistration;
import com.klef.sdp.model.Election;
import com.klef.sdp.model.RegistrationStatus;
import com.klef.sdp.model.Voter;
import com.klef.sdp.service.AdminService;
import com.klef.sdp.service.CandidateElectionRegistrationService;
import com.klef.sdp.service.VoteService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private CandidateElectionRegistrationService registrationService;
    
    @Autowired
    private VoteService voteService;

    // ✅ Admin Login
    @PostMapping("/login")
    public ResponseEntity<?> checkAdminLogin(@RequestBody Admin admin) {
        try {
            Admin loggedInAdmin = adminService.checkAdminLogin(admin.getUsername(), admin.getPassword());

            if (loggedInAdmin != null) {
                return ResponseEntity.ok(loggedInAdmin);
            } else {
                return ResponseEntity.status(401).body("Invalid Username or Password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    // ✅ Add Election using DTO
    @PostMapping("/addelection")
    public ResponseEntity<String> addElection(@RequestBody ElectionDTO dto) {
        try {
            System.out.println("Received ElectionDTO:");
            System.out.println("Name: " + dto.getName());
            System.out.println("Start Date: " + dto.getStartDate());
            System.out.println("End Date: " + dto.getEndDate());
            System.out.println("Type: " + dto.getElectionType());
            System.out.println("Admin ID: " + dto.getAdmin_id());

            if (dto.getAdmin_id() == null) {
                return ResponseEntity.badRequest().body("Admin ID cannot be null.");
            }

            Admin admin = adminService.getAdminById(dto.getAdmin_id());
            if (admin == null) {
                return ResponseEntity.status(404).body("Admin not found with ID: " + dto.getAdmin_id());
            }

            Election election = new Election();
            election.setName(dto.getName());
            election.setDescription(dto.getDescription());
            election.setStartDate(dto.getStartDate());
            election.setEndDate(dto.getEndDate());
            election.setElectionType(dto.getElectionType());
            election.setCity(dto.getCity());
            election.setStation(dto.getStation());
            election.setAdmin(admin);

            String result = adminService.addElection(election, dto.getAdmin_id());
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to Add Election: " + e.getMessage());
        }
    }

    @GetMapping("/viewallvoters")
    public ResponseEntity<List<Voter>> viewAllVoters() {
        try {
            List<Voter> voters = adminService.viewAllVoters();
            return ResponseEntity.ok(voters);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/viewallcandidates")
    public ResponseEntity<List<Candidate>> viewAllCandidates() {
        try {
            List<Candidate> candidates = adminService.viewAllCandidates();
            return ResponseEntity.ok(candidates);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/votercount")
    public ResponseEntity<Long> getVoterCount() {
        return ResponseEntity.ok(adminService.getVoterCount());
    }

    @GetMapping("/candidatecount")
    public ResponseEntity<Long> getCandidateCount() {
        return ResponseEntity.ok(adminService.getCandidateCount());
    }

    @GetMapping("/totalvotes")
    public ResponseEntity<Long> getTotalVotes() {
        return ResponseEntity.ok(adminService.getTotalVotes());
    }
    
    @GetMapping("/candidate-registrations")
    public ResponseEntity<?> getCandidateRegistrations(
            @RequestParam(value = "status", required = false) String status) {
        try {
            List<CandidateElectionRegistration> registrations;
            if (status != null && !status.isBlank()) {
                RegistrationStatus registrationStatus = RegistrationStatus.valueOf(status.toUpperCase());
                registrations = registrationService.getRegistrationsByStatus(registrationStatus);
            } else {
                registrations = registrationService.getAllRegistrations();
            }
            List<CandidateElectionRegistrationDTO> response = registrations.stream()
                    .map(CandidateElectionRegistrationDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value. Allowed values: PENDING, APPROVED, REJECTED");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch candidate registrations: " + e.getMessage());
        }
    }

    @PutMapping("/candidate-registrations/{registrationId}/status")
    public ResponseEntity<?> updateCandidateRegistrationStatus(
            @PathVariable Long registrationId,
            @RequestBody UpdateRegistrationStatusRequest request) {
        try {
            RegistrationStatus status = RegistrationStatus.valueOf(request.getStatus().toUpperCase());
            CandidateElectionRegistration updated = registrationService
                    .updateRegistrationStatus(registrationId, status);
            return ResponseEntity.ok(CandidateElectionRegistrationDTO.fromEntity(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update registration status: " + e.getMessage());
        }
    }
    
    @GetMapping("/results")
    public ResponseEntity<?> getElectionResults() {
        try {
            return ResponseEntity.ok(voteService.getElectionResults());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch election results: " + e.getMessage());
        }
    }
}

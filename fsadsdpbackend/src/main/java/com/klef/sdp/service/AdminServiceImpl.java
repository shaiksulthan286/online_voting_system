package com.klef.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Admin;
import com.klef.sdp.model.Candidate;
import com.klef.sdp.model.Election;
import com.klef.sdp.model.Voter;
import com.klef.sdp.repository.AdminRepository;
import com.klef.sdp.repository.CandidateRepository;
import com.klef.sdp.repository.ElectionRepository;
import com.klef.sdp.repository.VoteRepository;
import com.klef.sdp.repository.VoterRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    // Checks Admin login credentials
    @Override
    public Admin checkAdminLogin(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username, password);
    }

    // Returns total count of voters
    @Override
    public long getVoterCount() {
        return voterRepository.count();
    }

    // Returns total count of candidates
    @Override
    public long getCandidateCount() {
        return candidateRepository.count();
    }

    // Placeholder for getting total votes (implement logic based on your domain model)
    @Override
    public long getTotalVotes() {
        return voteRepository.count();
    }

    // Add election method with adminId as parameter
    @Override
    public String addElection(Election election, Long adminId) {
        try {
            Admin admin = adminRepository.findById(adminId).orElse(null);
            if (admin == null) {
                return "Admin not found for ID: " + adminId;
            }

            election.setAdmin(admin);
            electionRepository.save(election);
            return "Election Added Successfully";
        } catch (Exception e) {
            return "Error adding election: " + e.getMessage();
        }
    }

    // Get admin by ID (corrected for Long type)
    @Override
    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }

    // View all voters
    @Override
    public List<Voter> viewAllVoters() {
        return voterRepository.findAll();
    }

    // ✅ View all candidates
    @Override
    public List<Candidate> viewAllCandidates() {
        return candidateRepository.findAll();
    }
    
    

}
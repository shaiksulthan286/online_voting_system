package com.klef.sdp.service;

import java.util.List;
import com.klef.sdp.model.Admin;
import com.klef.sdp.model.Candidate;
import com.klef.sdp.model.Election;
import com.klef.sdp.model.Voter;

public interface AdminService {

    // Method to check admin login by username and password
    public Admin checkAdminLogin(String username, String password);

    // Method to get the total count of voters
    public long getVoterCount();

    // Method to get the total count of candidates
    public long getCandidateCount();

    // Method to get the total number of votes
    public long getTotalVotes();

    // Method to add a new election with adminId
    public String addElection(Election election, Long adminId);

    // Method to get admin by ID
    public Admin getAdminById(Long adminId);

    // Method to view all voters
    public List<Voter> viewAllVoters();

    // ✅ New: Method to view all candidates
    public List<Candidate> viewAllCandidates();
    

}
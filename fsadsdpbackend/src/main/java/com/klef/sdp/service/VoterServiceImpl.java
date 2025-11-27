package com.klef.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Election;
import com.klef.sdp.model.Voter;
import com.klef.sdp.repository.ElectionRepository;
import com.klef.sdp.repository.VoterRepository;

@Service
public class VoterServiceImpl implements VoterService
{
    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private ElectionRepository electionRepository; // ✅ Added to fetch elections

    @Override
    public String voterregistration(Voter voter)
    {
        voterRepository.save(voter);
        return "Voter Registered Successfully";
    }

    @Override
    public Voter checkvoterlogin(String username, String password)
    {
        return voterRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public String updatevoterprofile(Voter voter) 
    {
        Optional<Voter> optionalVoter = voterRepository.findById(voter.getId());
        if (optionalVoter.isPresent()) 
        {
            Voter existingVoter = optionalVoter.get();
            existingVoter.setName(voter.getName());
            existingVoter.setGender(voter.getGender());
            existingVoter.setDob(voter.getDob());
            existingVoter.setEmail(voter.getEmail());
            existingVoter.setUsername(voter.getUsername());
            existingVoter.setPassword(voter.getPassword());
            existingVoter.setMobileno(voter.getMobileno());
            existingVoter.setVotingArea(voter.getVotingArea());
            existingVoter.setVoterID(voter.getVoterID());
            existingVoter.setAadharID(voter.getAadharID());

            voterRepository.save(existingVoter);
            return "Voter profile updated successfully.";
        } 
        else 
        {
            return "Voter ID not found. Update failed.";
        }
    }

    @Override
    public Voter getVoterById(int vid)
    {
        Optional<Voter> optionalVoter = voterRepository.findById(vid);
        return optionalVoter.orElse(null);
    }

    @Override
    public long getVoterCount()
    {
        return voterRepository.count();
    }

    // ✅ NEW METHOD — To allow voters to view all elections added by Admin
    @Override
    public List<Election> viewAllElections()
    {
        return electionRepository.findAll();
    }
}

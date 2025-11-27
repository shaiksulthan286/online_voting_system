package com.klef.sdp.service;

import java.util.List;
import com.klef.sdp.model.Election;
import com.klef.sdp.model.Voter;

public interface VoterService 
{
    public String voterregistration(Voter voter);

    public Voter checkvoterlogin(String username, String password);

    public Voter getVoterById(int vid);

    public String updatevoterprofile(Voter voter);

    public long getVoterCount();

    // ✅ New method to allow voters to view elections added by admin
    public List<Election> viewAllElections();
}

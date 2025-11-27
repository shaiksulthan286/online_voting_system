package com.klef.sdp.service;

import com.klef.sdp.model.Candidate;

public interface CandidateService {
    String registerCandidate(Candidate candidate);
    Candidate checkCandidateLogin(String uname, String pwd);
    String updateCandidateProfile(Candidate candidate);
	String candidateRegistration(Candidate candidate);
	Candidate getCandidateById(int cid);
}

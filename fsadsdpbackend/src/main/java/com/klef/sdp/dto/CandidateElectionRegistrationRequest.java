package com.klef.sdp.dto;

public class CandidateElectionRegistrationRequest {

    private Integer candidateId;
    private Integer electionId;

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }
}


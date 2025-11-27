package com.klef.sdp.dto;

import java.util.ArrayList;
import java.util.List;

public class ElectionResultDTO {

    private Integer electionId;
    private String electionName;
    private long totalVotes;
    private CandidateResultDTO winner;
    private List<CandidateResultDTO> candidates = new ArrayList<>();

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public CandidateResultDTO getWinner() {
        return winner;
    }

    public void setWinner(CandidateResultDTO winner) {
        this.winner = winner;
    }

    public List<CandidateResultDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateResultDTO> candidates) {
        this.candidates = candidates;
    }
}



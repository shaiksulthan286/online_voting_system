package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.dto.ElectionResultDTO;
import com.klef.sdp.model.Vote;

public interface VoteService {

    Vote castVote(Integer voterId, Integer electionId, Integer candidateId);

    long getTotalVotes();

    List<ElectionResultDTO> getElectionResults();
}


package com.klef.sdp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.klef.sdp.dto.CandidateResultDTO;
import com.klef.sdp.dto.ElectionResultDTO;
import com.klef.sdp.model.Candidate;
import com.klef.sdp.model.CandidateElectionRegistration;
import com.klef.sdp.model.Election;
import com.klef.sdp.model.RegistrationStatus;
import com.klef.sdp.model.Vote;
import com.klef.sdp.model.Voter;
import com.klef.sdp.repository.CandidateElectionRegistrationRepository;
import com.klef.sdp.repository.CandidateRepository;
import com.klef.sdp.repository.ElectionRepository;
import com.klef.sdp.repository.VoteRepository;
import com.klef.sdp.repository.VoterRepository;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateElectionRegistrationRepository registrationRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    @Transactional
    public Vote castVote(Integer voterId, Integer electionId, Integer candidateId) {
        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new IllegalArgumentException("Voter not found for id: " + voterId));

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new IllegalArgumentException("Election not found for id: " + electionId));

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found for id: " + candidateId));

        if (voteRepository.findByVoterIdAndElectionId(voterId, electionId).isPresent()) {
            throw new IllegalStateException("You have already cast your vote for this election.");
        }

        CandidateElectionRegistration registration = registrationRepository
                .findByCandidateIdAndElectionId(candidateId, electionId)
                .orElseThrow(() -> new IllegalArgumentException("Candidate is not registered for this election."));

        if (registration.getStatus() != RegistrationStatus.APPROVED) {
            throw new IllegalStateException("Candidate registration must be approved to receive votes.");
        }

        LocalDate today = LocalDate.now();
        if (today.isBefore(election.getStartDate())) {
            throw new IllegalStateException("Voting has not started for this election.");
        }
        if (today.isAfter(election.getEndDate())) {
            throw new IllegalStateException("Voting period has ended for this election.");
        }

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setElection(election);
        vote.setCandidate(candidate);

        return voteRepository.save(vote);
    }

    @Override
    public long getTotalVotes() {
        return voteRepository.count();
    }

    @Override
    public List<ElectionResultDTO> getElectionResults() {
        Map<Integer, ElectionResultDTO> electionResults = new LinkedHashMap<>();

        List<CandidateElectionRegistration> approvedRegistrations = registrationRepository
                .findByStatus(RegistrationStatus.APPROVED);

        for (CandidateElectionRegistration registration : approvedRegistrations) {
            Election election = registration.getElection();
            Candidate candidate = registration.getCandidate();

            ElectionResultDTO resultDTO = electionResults.computeIfAbsent(election.getId(), id -> {
                ElectionResultDTO dto = new ElectionResultDTO();
                dto.setElectionId(election.getId());
                dto.setElectionName(election.getName());
                dto.setCandidates(new ArrayList<>());
                return dto;
            });

            ensureCandidateEntry(resultDTO, candidate);
        }

        List<Vote> votes = voteRepository.findAll();
        for (Vote vote : votes) {
            Election election = vote.getElection();
            Candidate candidate = vote.getCandidate();

            ElectionResultDTO resultDTO = electionResults.computeIfAbsent(election.getId(), id -> {
                ElectionResultDTO dto = new ElectionResultDTO();
                dto.setElectionId(election.getId());
                dto.setElectionName(election.getName());
                dto.setCandidates(new ArrayList<>());
                return dto;
            });

            CandidateResultDTO candidateResult = ensureCandidateEntry(resultDTO, candidate);
            candidateResult.setVotes(candidateResult.getVotes() + 1);
            resultDTO.setTotalVotes(resultDTO.getTotalVotes() + 1);
        }

        for (ElectionResultDTO resultDTO : electionResults.values()) {
            long totalVotes = resultDTO.getTotalVotes();
            CandidateResultDTO winner = null;
            for (CandidateResultDTO candidateResult : resultDTO.getCandidates()) {
                double percentage = totalVotes == 0 ? 0.0 : (candidateResult.getVotes() * 100.0) / totalVotes;
                candidateResult.setPercentage(Math.round(percentage * 100.0) / 100.0);
                if (winner == null || candidateResult.getVotes() > winner.getVotes()) {
                    winner = candidateResult;
                }
            }
            if (winner != null) {
                winner.setWinner(true);
                resultDTO.setWinner(winner);
            }
        }

        return new ArrayList<>(electionResults.values());
    }

    private CandidateResultDTO ensureCandidateEntry(ElectionResultDTO electionResult, Candidate candidate) {
        return electionResult.getCandidates().stream()
                .filter(c -> c.getCandidateId().equals(candidate.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CandidateResultDTO dto = new CandidateResultDTO();
                    dto.setCandidateId(candidate.getId());
                    dto.setCandidateName(candidate.getName());
                    electionResult.getCandidates().add(dto);
                    return dto;
                });
    }
}


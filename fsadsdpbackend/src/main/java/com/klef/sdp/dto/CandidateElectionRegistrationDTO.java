package com.klef.sdp.dto;

import java.time.LocalDateTime;

import com.klef.sdp.model.CandidateElectionRegistration;

public class CandidateElectionRegistrationDTO {

    private Long id;
    private Integer candidateId;
    private String candidateName;
    private Integer electionId;
    private String electionName;
    private String electionType;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CandidateElectionRegistrationDTO fromEntity(CandidateElectionRegistration registration) {
        CandidateElectionRegistrationDTO dto = new CandidateElectionRegistrationDTO();
        dto.setId(registration.getId());
        dto.setCandidateId(registration.getCandidate().getId());
        dto.setCandidateName(registration.getCandidate().getName());
        dto.setElectionId(registration.getElection().getId());
        dto.setElectionName(registration.getElection().getName());
        dto.setElectionType(registration.getElection().getElectionType());
        dto.setStatus(registration.getStatus().name());
        dto.setCreatedAt(registration.getCreatedAt());
        dto.setUpdatedAt(registration.getUpdatedAt());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

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

    public String getElectionType() {
        return electionType;
    }

    public void setElectionType(String electionType) {
        this.electionType = electionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}


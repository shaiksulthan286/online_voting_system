package com.klef.sdp.dto;

import com.klef.sdp.model.Candidate;
import com.klef.sdp.model.CandidateElectionRegistration;

public class CandidateSummaryDTO {

    private Integer candidateId;
    private String name;
    private String party;
    private String area;
    private Long registrationId;

    public static CandidateSummaryDTO fromEntity(CandidateElectionRegistration registration) {
        CandidateSummaryDTO dto = new CandidateSummaryDTO();
        Candidate candidate = registration.getCandidate();
        dto.setCandidateId(candidate.getId());
        dto.setName(candidate.getName());
        dto.setParty(candidate.getParty());
        dto.setArea(candidate.getArea());
        dto.setRegistrationId(registration.getId());
        return dto;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }
}


package com.klef.sdp.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.klef.sdp.model.CandidateElectionRegistration;
import com.klef.sdp.model.Election;

public class ElectionWithCandidatesDTO {

    private Integer electionId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String electionType;
    private String city;
    private String station;
    private List<CandidateSummaryDTO> candidates;

    public static ElectionWithCandidatesDTO fromEntities(Election election,
            List<CandidateElectionRegistration> registrations) {
        ElectionWithCandidatesDTO dto = new ElectionWithCandidatesDTO();
        dto.setElectionId(election.getId());
        dto.setName(election.getName());
        dto.setDescription(election.getDescription());
        dto.setStartDate(election.getStartDate());
        dto.setEndDate(election.getEndDate());
        dto.setElectionType(election.getElectionType());
        dto.setCity(election.getCity());
        dto.setStation(election.getStation());
        dto.setCandidates(registrations.stream()
                .map(CandidateSummaryDTO::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getElectionType() {
        return electionType;
    }

    public void setElectionType(String electionType) {
        this.electionType = electionType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<CandidateSummaryDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateSummaryDTO> candidates) {
        this.candidates = candidates;
    }
}


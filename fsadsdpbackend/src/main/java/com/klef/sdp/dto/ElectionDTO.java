package com.klef.sdp.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ElectionDTO {
    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String electionType;
    private String city;
    private String station;
    private Long admin_id; // ✅ Must be provided from frontend

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getElectionType() { return electionType; }
    public void setElectionType(String electionType) { this.electionType = electionType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStation() { return station; }
    public void setStation(String station) { this.station = station; }

    public Long getAdmin_id() { return admin_id; }
    public void setAdmin_id(Long admin_id) { this.admin_id = admin_id; }
}

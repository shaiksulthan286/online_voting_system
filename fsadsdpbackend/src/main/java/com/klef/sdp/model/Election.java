package com.klef.sdp.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "election_table")
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Auto increment handled by DB
    private Integer id; // ✅ Use wrapper type (not primitive int)

    @Column(nullable = false, length = 100)
    private String name; // Name of the election

    @Column(nullable = false, length = 500)
    private String description; // Description of the election

    @Column(nullable = false)
    private LocalDate startDate; // Start date of the election

    @Column(nullable = false)
    private LocalDate endDate; // End date of the election

    @Column(nullable = false, length = 100)
    private String electionType; // Type of the election (e.g., Local, National)

    @Column(nullable = false, length = 100)
    private String city; // City where the election is taking place

    @Column(nullable = false, length = 100)
    private String station; // Station or location of the election

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false) // ✅ Removed cascade to prevent unwanted saves
    private Admin admin; // Link to Admin entity

    // ✅ Default Constructor (required by JPA)
    public Election() {}

    // ✅ Parameterized Constructor (optional)
    public Election(String name, String description, LocalDate startDate, LocalDate endDate,
                    String electionType, String city, String station, Admin admin) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.electionType = electionType;
        this.city = city;
        this.station = station;
        this.admin = admin;
    }

    // ✅ Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Election{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", electionType='" + electionType + '\'' +
                ", city='" + city + '\'' +
                ", station='" + station + '\'' +
                ", admin=" + (admin != null ? admin.getId() : null) +
                '}';
    }
}

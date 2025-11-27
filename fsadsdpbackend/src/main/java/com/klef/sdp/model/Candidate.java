package com.klef.sdp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_table")
public class Candidate {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private int id;
  
    @Column(name = "candidate_name", length = 50, nullable = false)
    private String name;
  
    @Column(name = "candidate_gender", length = 10, nullable = false)
    private String gender;
  
    @Column(name = "candidate_dob", length = 20, nullable = false)
    private String dob;
  
    @Column(name = "candidate_email", length = 50, nullable = false, unique = true)
    private String email;
  
    @Column(name = "candidate_username", length = 50, nullable = false, unique = true)
    private String username;
  
    @Column(name = "candidate_password", length = 50, nullable = false)
    private String password;
  
    @Column(name = "candidate_mobileno", length = 20, nullable = false, unique = true)
    private String mobileno;
  
    @Column(name = "candidate_party", length = 50, nullable = false)
    private String party;
  
    @Column(name = "candidate_area", length = 50, nullable = false)
    private String area;  // Area or region the candidate is contesting from
  
    // Getter and Setter methods
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
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
}

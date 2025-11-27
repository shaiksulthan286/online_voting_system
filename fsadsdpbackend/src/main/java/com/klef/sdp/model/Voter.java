package com.klef.sdp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "voter_table")
public class Voter 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "voter_id")
	private int id;
	@Column(name = "voter_name", length = 50, nullable = false)
	private String name;
	@Column(name = "voter_gender", length = 10, nullable = false)
	private String gender;
    @Column(name = "voter_dob", length = 20, nullable = false)
	private String dob;
	@Column(name = "voter_email", length = 50, nullable = false, unique = true)
	private String email;
	@Column(name = "voter_username", length = 50, nullable = false, unique = true)
	private String username;
	@Column(name = "voter_password", length = 50, nullable = false)
	private String password;
	@Column(name = "voter_mobileno", length = 20, nullable = false, unique = true)
	private String mobileno;
	@Column(name = "voting_area", length = 50, nullable = false)
	private String votingArea;
	@Column(name = "voter_uniqueid", length = 20, nullable = false, unique = true)
	private String voterID;
	@Column(name = "voter_aadharid", length = 20, nullable = false, unique = true)
	private String aadharID;
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
	public String getVotingArea() {
		return votingArea;
	}
	public void setVotingArea(String votingArea) {
		this.votingArea = votingArea;
	}
	public String getVoterID() {
		return voterID;
	}
	public void setVoterID(String voterID) {
		this.voterID = voterID;
	}
	public String getAadharID() {
		return aadharID;
	}
	public void setAadharID(String aadharID) {
		this.aadharID = aadharID;
	}

}

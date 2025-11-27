package com.klef.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.sdp.model.Voter;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Integer>
{
	public Voter findByUsernameAndPassword(String username, String password);
}

package com.klef.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.sdp.model.Election;
import com.klef.sdp.model.Admin;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {

    
}
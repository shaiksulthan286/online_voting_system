package com.klef.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Election;
import com.klef.sdp.repository.ElectionRepository;

@Service
public class ElectionServiceImpl implements ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Override
    public Election addElection(Election election) {
        return electionRepository.save(election);
    }

    @Override
    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }
}



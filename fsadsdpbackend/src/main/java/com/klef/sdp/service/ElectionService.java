package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.model.Election;

public interface ElectionService {
    Election addElection(Election election);
    List<Election> getAllElections(); // optional for listing
}

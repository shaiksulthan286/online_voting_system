package com.klef.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.sdp.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>
{
  public Admin findByUsernameAndPassword(String username, String password);
  
    
}

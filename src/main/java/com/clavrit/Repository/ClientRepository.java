package com.clavrit.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	boolean existsByCompanyIgnoreCaseAndEmailIgnoreCase(String company, String email);
    
}

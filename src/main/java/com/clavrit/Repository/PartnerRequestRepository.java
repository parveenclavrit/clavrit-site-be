package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clavrit.Entity.PartnerRequest;

public interface PartnerRequestRepository extends JpaRepository<PartnerRequest, Long>{

	boolean existsByCompanyNameIgnoreCaseAndEmailIgnoreCase(String companyName, String email);
	
}

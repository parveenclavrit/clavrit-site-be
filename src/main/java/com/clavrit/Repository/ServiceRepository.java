package com.clavrit.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.ClavritService;

@Repository
public interface ServiceRepository extends JpaRepository<ClavritService, Long> {
	
	Optional<ClavritService> findByNameAndDescription(String name, String description);

}

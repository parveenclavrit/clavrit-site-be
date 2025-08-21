package com.clavrit.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.JobDetail;


@Repository
public interface JobDetailRepository extends JpaRepository<JobDetail, Long> {
	
	boolean existsByJobDesignationIgnoreCase(String jobDesignation);

	Optional<JobDetail> findByJobDesignation(String JobDesignation);

}

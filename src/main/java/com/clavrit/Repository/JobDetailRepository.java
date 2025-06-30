package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clavrit.Entity.JobDetail;

public interface JobDetailRepository extends JpaRepository<JobDetail, Long> {
}

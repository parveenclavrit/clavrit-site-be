package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.JobDetail;


@Repository
public interface JobDetailRepository extends JpaRepository<JobDetail, Long> {
}

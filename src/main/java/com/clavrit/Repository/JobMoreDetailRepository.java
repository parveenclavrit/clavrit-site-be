package com.clavrit.Repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.JobMoreDetail;

@Repository
public interface JobMoreDetailRepository extends JpaRepository<JobMoreDetail, Long> {
}


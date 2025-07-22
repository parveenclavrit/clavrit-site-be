package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clavrit.Entity.Technologies;

public interface TechnologiesRepository extends JpaRepository<Technologies, Long> {

}

package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	boolean existsByTitleIgnoreCase(String title);

}

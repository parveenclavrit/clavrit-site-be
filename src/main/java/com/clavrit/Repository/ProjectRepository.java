package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clavrit.Entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}

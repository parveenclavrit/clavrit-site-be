package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clavrit.Entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long>{

}

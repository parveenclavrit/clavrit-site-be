package com.clavrit.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.Blog;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{

	boolean existsByTitleIgnoreCaseAndAuthorNameIgnoreCase(String title, String authorName);
	
	Optional<Blog> findBySlug(String slug);
}

package com.clavrit;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.clavrit.Entity.AppUser;
import com.clavrit.Repository.AppUserRepository;

@EnableWebMvc
@SpringBootApplication
public class AppRunner extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppRunner.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AppRunner.class);
	}
	
	@Bean
    public CommandLineRunner initUsers(AppUserRepository repo) {
        return args -> {
            createUserIfNotExists(repo, 
                "admin@clavrit.com", "Clavrit@12345", "ADMIN");

            createUserIfNotExists(repo, 
                "hr@clavrit.com", "Clavrit@12345", "HR");
        };
    }

    private void createUserIfNotExists(AppUserRepository repo,
                                       String email, String rawPassword, String role) {
        Optional<AppUser> existingUser = repo.findByEmail(email);
        if (!existingUser.isPresent()) {
            AppUser user = new AppUser();
            user.setEmail(email);
            user.setPassword(rawPassword);
            user.setRole(role);
            repo.save(user);
            
        } else {
            
        }
    }
}

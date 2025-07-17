package com.clavrit.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.Dto.LoginRequestDto;
import com.clavrit.Entity.AppUser;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Repository.AppUserRepository;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AppUserRepository userRepository;

    
    @PostMapping("/login")
    public ApisResponse login(@RequestBody LoginRequestDto request) {
        Optional<AppUser> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            AppUser user = userOpt.get();

            if (request.getPassword().equals(user.getPassword())) {
                return new ApisResponse(ApiStatus.OK,"Login successful", user);
            } else {
                return new ApisResponse(ApiStatus.BAD_REQUEST,"Invalid password");
            }
        } else {
            return new ApisResponse(ApiStatus.NOT_FOUND,"User not found", false);
        }
    }

}

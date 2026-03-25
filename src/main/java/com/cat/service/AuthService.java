package com.cat.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cat.dto.Login;
import com.cat.dto.LoginResponse;
import com.cat.dto.Register;
import com.cat.entity.User;
import com.cat.repository.UserRepo;
import com.cat.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ========== REGISTER =========
    public String register(Register register){
        if(userRepo.findByUserName(register.getUserName()).isPresent()){
            throw new RuntimeException("bro you are late, someone already captured it");
        }

        User user = new User();
        user.setUserName(register.getUserName());
        user.setPassBcrypt(passwordEncoder.encode(register.getPassword()));

        userRepo.save(user);

        return "congo, nice name btw";
    }

    // =========== LOGIN ===========
    public LoginResponse login(Login login){
        User user = userRepo.findByUserName(login.getUserName())
                        .orElseThrow(() -> new RuntimeException("no bro, u r not here"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassBcrypt())) {
            throw new RuntimeException("dont u remember your password----------YOU MORON");
        }

        String token = jwtUtil.generateToken(login.getUserName());
        return new LoginResponse( user.getUserName(),token);
    }

}

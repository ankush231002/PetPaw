package com.cat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.dto.Login;
import com.cat.dto.LoginResponse;
import com.cat.dto.Register;
import com.cat.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    // ============ REGISTER ===============
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Register register){
        String message = authService.register(register);
        
        return ResponseEntity.ok(message);
    }

    // ========= LOGIN ==========
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody Login login){
        LoginResponse loginResponse = authService.login(login);

        return ResponseEntity.ok(loginResponse);
    }
}

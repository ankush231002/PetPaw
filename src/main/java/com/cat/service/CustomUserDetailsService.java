package com.cat.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cat.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        com.cat.entity.User user = userRepo.findByUserName(name)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));

        return new org.springframework.security.core.userdetails.User(
            user.getUserName(),
            user.getPassBcrypt(),
            new ArrayList<>()
        );
    }
}
package com.cat.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cat.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeepAliveService {

    private final UserRepo userRepo;

    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void keepAlive() {
        userRepo.count();
    }
}
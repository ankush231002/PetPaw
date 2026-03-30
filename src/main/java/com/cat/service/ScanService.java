package com.cat.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cat.dto.ScanRequestDTO;
import com.cat.dto.ScanResponseDTO;
import com.cat.entity.Info;
import com.cat.entity.ScanEvent;
import com.cat.repository.InfoRepo;
import com.cat.repository.ScanRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScanService {
    
    private final ScanRepo scanRepo;
    private final InfoRepo infoRepo;
 
    public List<ScanResponseDTO> getScans(String publicUrl) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Info info = infoRepo.findByPublicUrl(publicUrl)
                .orElseThrow(() -> new RuntimeException("pet not found"));

        if (!info.getUser().getUserName().equals(userName)) {
            throw new RuntimeException("this is not your pet");
        }

        return scanRepo.findByInfoOrderByScanTimeDesc(info)
                .stream()
                .map(event -> new ScanResponseDTO(
                        publicUrl,
                        event.getEventType(),
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getAccuracy(),
                        event.getScanTime().toInstant(ZoneOffset.UTC)
                ))
                .collect(Collectors.toList());
    }

    public ScanResponseDTO save(String publicUrl, ScanRequestDTO dto) {
        Info info = infoRepo.findByPublicUrl(publicUrl)
                .orElseThrow(() -> new RuntimeException("pet not found"));

        ScanEvent scanEvent = new ScanEvent();
        scanEvent.setInfo(info);
        scanEvent.setLatitude(dto.getLatitude());
        scanEvent.setLongitude(dto.getLongitude());
        scanEvent.setAccuracy(dto.getAccuracy());
        scanEvent.setEventType(dto.getEventType());
        scanEvent.setScanTime(LocalDateTime.now());

        scanRepo.save(scanEvent);

        ScanResponseDTO response = new ScanResponseDTO();
        response.setPublicUrl(publicUrl);
        response.setEventType(scanEvent.getEventType());
        response.setLatitude(scanEvent.getLatitude());
        response.setLongitude(scanEvent.getLongitude());
        response.setAccuracy(scanEvent.getAccuracy());
        response.setScanTime(scanEvent.getScanTime().toInstant(ZoneOffset.UTC));

        return response;
    }
}

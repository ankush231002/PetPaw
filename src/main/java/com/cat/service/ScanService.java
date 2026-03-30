package com.cat.service;

import java.time.LocalDateTime;
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

    public void recordScan(String publicUrl ,ScanRequestDTO dto){
        Info info = infoRepo.findByPublicUrl(publicUrl)
                        .orElseThrow(()-> new RuntimeException("pet not found"));
        
        ScanEvent scanEvent = new ScanEvent();
        scanEvent.setLatitude(dto.getLatitude());
        scanEvent.setLongitude(dto.getLongitude());
        scanEvent.setScanTime(LocalDateTime.now());
        scanEvent.setInfo(info);

        scanRepo.save(scanEvent);
    } 

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
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getScanTime()
                ))
                .collect(Collectors.toList());
    }
}

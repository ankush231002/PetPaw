package com.cat.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 
    public Page<ScanResponseDTO> getScans(String publicUrl, int page, int size) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Info info = infoRepo.findByPublicUrl(publicUrl)
                .orElseThrow(() -> new RuntimeException("pet not found"));

        if (!info.getUser().getUserName().equals(userName)) {
            throw new RuntimeException("this is not your pet");
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        return scanRepo.findByInfoOrderByScanTimeDesc(info, pageRequest)
                .map(event -> new ScanResponseDTO(
                    event.getId(),
                    publicUrl,
                    event.getEventType(),
                    event.getLatitude(),
                    event.getLongitude(),
                    event.getAccuracy(),
                    event.getScanTime().toInstant(ZoneOffset.UTC)
                ));
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
        response.setId(scanEvent.getId());
        response.setPublicUrl(publicUrl);
        response.setEventType(scanEvent.getEventType());
        response.setLatitude(scanEvent.getLatitude());
        response.setLongitude(scanEvent.getLongitude());
        response.setAccuracy(scanEvent.getAccuracy());
        response.setScanTime(scanEvent.getScanTime().toInstant(ZoneOffset.UTC));

        return response;
    }

    public String delete(Long id){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        ScanEvent scanEvent = scanRepo.findById(id)
                            .orElseThrow(()-> new RuntimeException("scan not found"));

        if(!scanEvent.getInfo().getUser().getUserName().equals(userName)){
            throw new RuntimeException("this is not your pet");
        }

        scanRepo.deleteById(id);
        return "deleted successfully";
    }
}

package com.cat.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.cat.dto.ScanRequestDTO;
import com.cat.dto.ScanResponseDTO;
import com.cat.service.ScanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/scan")
@RequiredArgsConstructor
public class ScanController {

    private final ScanService scanService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/{publicUrl}")
    public ResponseEntity<Void> createScan(
            @PathVariable String publicUrl,
            @RequestBody ScanRequestDTO req
    ) {
        ScanResponseDTO saved = scanService.save(publicUrl, req);
        messagingTemplate.convertAndSend(
            "/topic/scan/" + publicUrl,
            saved
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{publicUrl}")
    public ResponseEntity<List<ScanResponseDTO>> getScans(@PathVariable String publicUrl) {
        return ResponseEntity.ok(scanService.getScans(publicUrl));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(Long id){
        return ResponseEntity.ok(scanService.delete(id));
    }
}
package com.cat.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.dto.ScanRequestDTO;
import com.cat.dto.ScanResponseDTO;
import com.cat.service.ScanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/scan")
@RequiredArgsConstructor
public class ScanController {

    private final ScanService scanService;

    @PostMapping("/{publicUrl}")
    public ResponseEntity<Void> recordScan(@PathVariable String publicUrl, @RequestBody ScanRequestDTO dto) {
        scanService.recordScan(publicUrl, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{publicUrl}")
    public ResponseEntity<List<ScanResponseDTO>> getRecord(@PathVariable String publicUrl){
        return ResponseEntity.ok(scanService.getScans(publicUrl));
    }
}

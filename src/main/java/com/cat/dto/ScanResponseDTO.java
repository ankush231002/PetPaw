package com.cat.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import com.cat.entity.ScanEventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanResponseDTO {

    private Long id;
    private String publicUrl;
    private ScanEventType eventType;
    private Double latitude;
    private Double longitude;
    private Double accuracy;
    private Instant scanTime;
}

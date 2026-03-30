package com.cat.dto;

import com.cat.entity.ScanEventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanRequestDTO {
    
    private Double latitude;
    private Double longitude;
    private Double accuracy;
    private ScanEventType eventType;
}

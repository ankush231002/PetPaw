package com.cat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanResponseDTO {

    private Double latitude;
    private Double longitude;
    private LocalDateTime scanTime;

}

package com.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoResponseDTO {

    private String petName;

    private String ownerName;

    private String phone;

    private String imagePath;
    
    private String publicUrl;
}

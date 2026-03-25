package com.cat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @NotBlank(message = "petname???")
    private String petName;

    @NotBlank(message = "ownerName????")
    private String ownerName;

    @NotBlank(message = "phone????")
    private String phone;

    private String imagePath;
    
    
}

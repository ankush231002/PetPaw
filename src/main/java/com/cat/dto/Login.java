package com.cat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @NotBlank(message = "user name?????????")
    private String userName;

    @NotBlank(message = "password?????")
    private String password;
}

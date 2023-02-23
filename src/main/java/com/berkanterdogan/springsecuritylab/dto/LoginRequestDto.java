package com.berkanterdogan.springsecuritylab.dto;

import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto extends BaseDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

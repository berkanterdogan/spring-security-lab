package com.berkanterdogan.springsecuritylab.dto;

import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDto extends BaseDTO {
    private String fieldName;
    private String errorMessage;
}

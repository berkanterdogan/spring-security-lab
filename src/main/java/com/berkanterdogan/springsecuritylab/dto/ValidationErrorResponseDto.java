package com.berkanterdogan.springsecuritylab.dto;

import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponseDto extends BaseDTO {

    private List<ValidationErrorDto> validationErrors = new ArrayList<>();
}

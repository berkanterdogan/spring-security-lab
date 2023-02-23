package com.berkanterdogan.springsecuritylab.dto;

import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;
import com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AppAuthorityDTO extends BaseDTO {

    private Long id;

    private AppAuthorityEnum appAuthorityEnum;

    @Builder
    public AppAuthorityDTO(Long id, AppAuthorityEnum appAuthorityEnum) {
        this.id = id;
        this.appAuthorityEnum = appAuthorityEnum;
    }
}

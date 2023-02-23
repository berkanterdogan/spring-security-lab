package com.berkanterdogan.springsecuritylab.dto;

import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;
import com.berkanterdogan.springsecuritylab.enums.AppRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class AppRoleDTO extends BaseDTO {

    private Long id;

    private AppRoleEnum appRoleEnum;

    private Set<AppAuthorityDTO> appAuthorityDTOList = new HashSet<>();

    @Builder
    public AppRoleDTO(Long id, AppRoleEnum appRoleEnum, Set<AppAuthorityDTO> appAuthorityDTOList) {
        this.id = id;
        this.appRoleEnum = appRoleEnum;
        this.appAuthorityDTOList = appAuthorityDTOList;
    }
}

package com.berkanterdogan.springsecuritylab.service.domain;


import com.berkanterdogan.springsecuritylab.domain.AppRole;
import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.service.domain.base.BaseDomainService;

import java.util.List;

public interface AppRoleService extends BaseDomainService<AppRole, AppRoleDTO, Long> {

    AppRoleDTO addAppAuthoritiesToAppRole(String roleName, List<AppAuthorityDTO> appAuthorityDTOList);
}

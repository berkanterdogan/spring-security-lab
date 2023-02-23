package com.berkanterdogan.springsecuritylab.service.domain;

import com.berkanterdogan.springsecuritylab.domain.AppUser;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.service.domain.base.BaseDomainService;

import java.util.List;
import java.util.Optional;

public interface AppUserService extends BaseDomainService<AppUser, AppUserDTO, Long> {

    AppUserDTO updateOwnUser(AppUserDTO appUserDTO);

    AppUserDTO findOwnUser();

    Optional<AppUserDTO> findByUsername(String username);

    AppUserDTO addAppRolesToAppUser(String username, List<AppRoleDTO> appRoleDTOList);

}

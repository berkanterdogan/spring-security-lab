package com.berkanterdogan.springsecuritylab.service.domain.impl;

import com.berkanterdogan.springsecuritylab.domain.AppRole;
import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.enums.AppRoleEnum;
import com.berkanterdogan.springsecuritylab.mapper.AppRoleMapper;
import com.berkanterdogan.springsecuritylab.mapper.base.BaseEntityDtoMapper;
import com.berkanterdogan.springsecuritylab.repository.AppRoleRepository;
import com.berkanterdogan.springsecuritylab.service.domain.AppRoleService;
import com.berkanterdogan.springsecuritylab.service.domain.base.impl.DefaultBaseDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultAppRoleService extends DefaultBaseDomainService<AppRole, AppRoleDTO, Long> implements AppRoleService {

    private final AppRoleRepository appRoleRepository;
    private final AppRoleMapper appRoleMapper;

    @Override
    @Transactional
    public AppRoleDTO addAppAuthoritiesToAppRole(String roleName, List<AppAuthorityDTO> appAuthorityDTOList) {
        AppRoleEnum appRoleEnum = AppRoleEnum.getEnumByName(roleName);
        if (appRoleEnum == null) {
            throw new IllegalArgumentException("Value of roleName parameter is not valid: " + roleName);
        }

        Optional<AppRole> appRoleOptional = this.appRoleRepository.findAppRoleByAppRoleEnum(appRoleEnum);
        AppRoleDTO appRoleDTO = null;
        if (appRoleOptional.isPresent()) {
            appRoleDTO = this.appRoleMapper.mapToDTO(appRoleOptional.get());
            appRoleDTO.getAppAuthorityDTOList().addAll(appAuthorityDTOList);
            appRoleDTO = super.save(appRoleDTO);
        }

        return appRoleDTO;
    }

    @Override
    public JpaRepository<AppRole, Long> getRepository() {
        return this.appRoleRepository;
    }

    @Override
    public BaseEntityDtoMapper<AppRole, AppRoleDTO> getMapper() {
        return this.appRoleMapper;
    }


}

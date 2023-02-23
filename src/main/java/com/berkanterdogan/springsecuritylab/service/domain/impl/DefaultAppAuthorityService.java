package com.berkanterdogan.springsecuritylab.service.domain.impl;

import com.berkanterdogan.springsecuritylab.domain.AppAuthority;
import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.mapper.AppAuthorityMapper;
import com.berkanterdogan.springsecuritylab.mapper.base.BaseEntityDtoMapper;
import com.berkanterdogan.springsecuritylab.repository.AppAuthorityRepository;
import com.berkanterdogan.springsecuritylab.service.domain.AppAuthorityService;
import com.berkanterdogan.springsecuritylab.service.domain.base.impl.DefaultBaseDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultAppAuthorityService extends DefaultBaseDomainService<AppAuthority, AppAuthorityDTO, Long> implements AppAuthorityService {

    private final AppAuthorityRepository appAuthorityRepository;
    private final AppAuthorityMapper appAuthorityMapper;

    @Override
    public JpaRepository<AppAuthority, Long> getRepository() {
        return this.appAuthorityRepository;
    }

    @Override
    public BaseEntityDtoMapper<AppAuthority, AppAuthorityDTO> getMapper() {
        return this.appAuthorityMapper;
    }


}

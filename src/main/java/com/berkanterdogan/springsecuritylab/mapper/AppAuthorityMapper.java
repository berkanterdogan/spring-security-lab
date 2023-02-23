package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.domain.AppAuthority;
import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.mapper.base.BaseEntityDtoMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AppAuthorityMapper extends BaseEntityDtoMapper<AppAuthority, AppAuthorityDTO> {

    @Override
    AppAuthorityDTO mapToDTO(AppAuthority entity);

    @Override
    AppAuthority mapToEntity(AppAuthorityDTO dto);

    @Override
    List<AppAuthorityDTO> mapToDTOList(List<AppAuthority> entityList);

    @Override
    List<AppAuthority> mapToEntityList(List<AppAuthorityDTO> dtoList);
}

package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.domain.AppRole;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.mapper.base.BaseEntityDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(uses = {AppAuthorityMapper.class})
public interface AppRoleMapper extends BaseEntityDtoMapper<AppRole, AppRoleDTO> {

    @Override
    @Mappings({
            @Mapping(target = "appAuthorityDTOList", source = "appAuthorityList")
    })
    AppRoleDTO mapToDTO(AppRole entity);

    @Override
    @Mappings({
            @Mapping(target = "appAuthorityList", source = "appAuthorityDTOList")
    })
    AppRole mapToEntity(AppRoleDTO dto);

    @Override
    List<AppRoleDTO> mapToDTOList(List<AppRole> entityList);

    @Override
    List<AppRole> mapToEntityList(List<AppRoleDTO> dtoList);
}

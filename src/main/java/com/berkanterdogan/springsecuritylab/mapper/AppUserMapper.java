package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.domain.AppUser;
import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.mapper.base.BaseEntityDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(uses = {AppRoleMapper.class})
public interface AppUserMapper extends BaseEntityDtoMapper<AppUser, AppUserDTO> {

    @Override
    @Mappings({
            @Mapping(target = "appRoleDTOList", source = "appRoleList")
    })
    AppUserDTO mapToDTO(AppUser entity);

    @Override
    @Mappings({
            @Mapping(target = "appRoleList", source = "appRoleDTOList")
    })
    AppUser mapToEntity(AppUserDTO dto);

    @Override
    List<AppUserDTO> mapToDTOList(List<AppUser> entityList);

    @Override
    List<AppUser> mapToEntityList(List<AppUserDTO> dtoList);
}

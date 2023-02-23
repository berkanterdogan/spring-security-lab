package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.dto.UpdateAppUserRequestDTO;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface AppUserDTOUpdateAppUserRequestDTOMapper {

    @BeforeMapping
    default void mapBefore(UpdateAppUserRequestDTO updateAppUserRequestDTO,
                           @MappingTarget AppUserDTO.AppUserDTOBuilder appUserDTOBuilder) {
        Set<Long> appRoleIdSet = updateAppUserRequestDTO.getAppRoleIdList();
        if (appRoleIdSet != null && !appRoleIdSet.isEmpty()) {

            Set<AppRoleDTO> appRoleDTOSet = appRoleIdSet.stream()
                    .map(id -> AppRoleDTO.builder().id(id).build())
                    .collect(Collectors.toSet());

            appUserDTOBuilder.appRoleDTOList(appRoleDTOSet);
        }
    }

    AppUserDTO mapToAppUserDTO(UpdateAppUserRequestDTO updateAppUserRequestDTO);

}

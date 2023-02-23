package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.dto.CreateAppUserRequestDTO;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface AppUserDTOCreateAppUserRequestDTOMapper {

    @BeforeMapping
    default void mapBefore(CreateAppUserRequestDTO createAppUserRequestDTO,
                                                    @MappingTarget AppUserDTO.AppUserDTOBuilder appUserDTOBuilder) {
        Set<Long> appRoleIdSet = createAppUserRequestDTO.getAppRoleIdList();
        if (appRoleIdSet != null && !appRoleIdSet.isEmpty()) {

            Set<AppRoleDTO> appRoleDTOSet = appRoleIdSet.stream()
                    .map(id -> AppRoleDTO.builder().id(id).build())
                    .collect(Collectors.toSet());

            appUserDTOBuilder.appRoleDTOList(appRoleDTOSet);
        }
    }

    AppUserDTO mapToAppUserDTO(CreateAppUserRequestDTO createAppUserRequestDTO);

    List<AppUserDTO> mapToAppUserDTOList(List<CreateAppUserRequestDTO> createAppUserRequestDTOList);


}

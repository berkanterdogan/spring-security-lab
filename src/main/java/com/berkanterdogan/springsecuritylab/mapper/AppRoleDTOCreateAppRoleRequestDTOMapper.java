package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.dto.CreateAppRoleRequestDTO;
import com.berkanterdogan.springsecuritylab.enums.AppRoleEnum;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface AppRoleDTOCreateAppRoleRequestDTOMapper {

    @BeforeMapping
    default void mapBefore(CreateAppRoleRequestDTO createAppRoleRequestDTO,
                                                    @MappingTarget AppRoleDTO.AppRoleDTOBuilder appRoleDTOBuilder) {
        AppRoleEnum appRoleEnum = AppRoleEnum.getEnumByName(createAppRoleRequestDTO.getRoleName());
        appRoleDTOBuilder.appRoleEnum(appRoleEnum);

        Set<Long> appAuthorityIdSet = createAppRoleRequestDTO.getAppAuthorityIdList();
        if (appAuthorityIdSet != null && !appAuthorityIdSet.isEmpty()) {

            Set<AppAuthorityDTO> appAuthorityDTOSet = appAuthorityIdSet.stream()
                    .map(id -> AppAuthorityDTO.builder().id(id).build())
                    .collect(Collectors.toSet());

            appRoleDTOBuilder.appAuthorityDTOList(appAuthorityDTOSet);
        }
    }

    AppRoleDTO mapToAppRoleDTO(CreateAppRoleRequestDTO createAppRoleRequestDTO);

    List<AppRoleDTO> mapToAppRoleDTOList(List<CreateAppRoleRequestDTO> createAppRoleRequestDTOList);


}

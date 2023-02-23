package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.dto.CreateAppAuthorityRequestDTO;
import com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface AppAuthorityDTOCreateAppAuthorityRequestDTOMapper {

    @BeforeMapping
    default void mapBefore(CreateAppAuthorityRequestDTO createAppAuthorityRequestDTO,
                                                    @MappingTarget AppAuthorityDTO.AppAuthorityDTOBuilder appAuthorityDTOBuilder) {
        AppAuthorityEnum appAuthorityEnum = AppAuthorityEnum.getEnumByName(createAppAuthorityRequestDTO.getAuthorityName());
        appAuthorityDTOBuilder.appAuthorityEnum(appAuthorityEnum);
    }

    AppAuthorityDTO mapToAppAuthorityDTO(CreateAppAuthorityRequestDTO createAppAuthorityRequestDTO);

    List<AppAuthorityDTO> mapToAppAuthorityDTOList(List<CreateAppAuthorityRequestDTO> createAppAuthorityRequestDTOList);


}

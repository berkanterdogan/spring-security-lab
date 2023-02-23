package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.dto.UpdateOwnAppUserRequestDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserDTOUpdateOwnAppUserRequestDTOMapper {

    AppUserDTO mapToAppUserDTO(UpdateOwnAppUserRequestDTO updateOwnAppUserRequestDTO);

}

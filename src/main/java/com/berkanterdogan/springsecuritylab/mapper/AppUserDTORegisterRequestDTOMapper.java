package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.dto.RegisterRequestDto;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserDTORegisterRequestDTOMapper {

    AppUserDTO mapToAppUserDTO(RegisterRequestDto registerRequestDto);

}

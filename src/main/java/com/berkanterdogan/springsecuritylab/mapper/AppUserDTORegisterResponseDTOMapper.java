package com.berkanterdogan.springsecuritylab.mapper;

import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.dto.RegisterResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserDTORegisterResponseDTOMapper {

    RegisterResponseDto mapToAppUserDTO(AppUserDTO appUserDTO);

}

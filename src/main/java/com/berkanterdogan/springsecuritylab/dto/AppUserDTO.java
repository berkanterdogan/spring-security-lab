package com.berkanterdogan.springsecuritylab.dto;

import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class AppUserDTO extends BaseDTO {

    private Long id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private Set<AppRoleDTO> appRoleDTOList = new HashSet<>();

    @Builder
    public AppUserDTO(Long id, String username, String password, String firstname, String lastname, Set<AppRoleDTO> appRoleDTOList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.appRoleDTOList = appRoleDTOList;
    }
}

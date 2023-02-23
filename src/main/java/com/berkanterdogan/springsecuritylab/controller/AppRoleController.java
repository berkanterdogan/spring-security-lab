package com.berkanterdogan.springsecuritylab.controller;

import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.dto.CreateAppRoleRequestDTO;
import com.berkanterdogan.springsecuritylab.mapper.AppRoleDTOCreateAppRoleRequestDTOMapper;
import com.berkanterdogan.springsecuritylab.service.domain.AppRoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/role")
public class AppRoleController {

    private final AppRoleService appRoleService;

    private final AppRoleDTOCreateAppRoleRequestDTOMapper appRoleDTOCreateAppRoleRequestDTOMapper;

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).CREATE_ROLE.name())")
    public ResponseEntity<AppRoleDTO> create(@RequestBody @Valid CreateAppRoleRequestDTO createAppRoleRequestDTO) {
        AppRoleDTO appRoleDTO = this.appRoleDTOCreateAppRoleRequestDTOMapper.mapToAppRoleDTO(createAppRoleRequestDTO);

        appRoleDTO = this.appRoleService.save(appRoleDTO);

        return ResponseEntity.ok(appRoleDTO);
    }

    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).CREATE_ROLE.name())")
    @PatchMapping(path = "/add-roles")
    public ResponseEntity<AppRoleDTO> addRoles(@RequestParam(name = "roleName") @NotBlank String roleName,
                                               @RequestBody @NotNull @NotEmpty List<AppAuthorityDTO> appAuthorityDTOList) {
        AppRoleDTO appRoleDTO = this.appRoleService.addAppAuthoritiesToAppRole(roleName, appAuthorityDTOList);

        return ResponseEntity.ok(appRoleDTO);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).READ_ROLE.name())")
    public ResponseEntity<AppRoleDTO> findById(@PathVariable(name = "id") @NotNull Long id) {
        Optional<AppRoleDTO> dtoOptional = this.appRoleService.findById(id);

        return ResponseEntity.ok(dtoOptional.orElse(null));
    }

    @GetMapping(path = "/find-all")
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).READ_ROLE.name())")
    public ResponseEntity<List<AppRoleDTO>> findAll() {
        List<AppRoleDTO> dtoList = this.appRoleService.findAll();

        return ResponseEntity.ok(dtoList);
    }

}

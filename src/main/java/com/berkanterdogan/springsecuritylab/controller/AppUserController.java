package com.berkanterdogan.springsecuritylab.controller;

import com.berkanterdogan.springsecuritylab.dto.*;
import com.berkanterdogan.springsecuritylab.mapper.AppUserDTOCreateAppUserRequestDTOMapper;
import com.berkanterdogan.springsecuritylab.mapper.AppUserDTOUpdateAppUserRequestDTOMapper;
import com.berkanterdogan.springsecuritylab.mapper.AppUserDTOUpdateOwnAppUserRequestDTOMapper;
import com.berkanterdogan.springsecuritylab.service.domain.AppUserService;
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
@RequestMapping(path = "/api/user")
public class AppUserController {

    private final AppUserService appUserService;

    private final AppUserDTOCreateAppUserRequestDTOMapper appUserDTOCreateAppUserRequestDTOMapper;

    private final AppUserDTOUpdateAppUserRequestDTOMapper appUserDTOUpdateAppUserRequestDTOMapper;

    private final AppUserDTOUpdateOwnAppUserRequestDTOMapper appUserDTOUpdateOwnAppUserRequestDTOMapper;

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).CREATE_USER.name())")
    public ResponseEntity<AppUserDTO> create(@RequestBody @Valid CreateAppUserRequestDTO createAppUserRequestDTO) {
        AppUserDTO appUserDTO = this.appUserDTOCreateAppUserRequestDTOMapper.mapToAppUserDTO(createAppUserRequestDTO);

        appUserDTO = this.appUserService.save(appUserDTO);

        return ResponseEntity.ok(appUserDTO);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).UPDATE_USER.name())")
    public ResponseEntity<AppUserDTO> update(@PathVariable(name = "id") @NotNull Long id,
                                             @RequestBody @Valid @NotNull UpdateAppUserRequestDTO updateAppUserRequestDTO) {
        AppUserDTO appUserDTO = this.appUserDTOUpdateAppUserRequestDTOMapper.mapToAppUserDTO(updateAppUserRequestDTO);
        appUserDTO.setId(id);

        appUserDTO = this.appUserService.save(appUserDTO);

        return ResponseEntity.ok(appUserDTO);
    }

    @PutMapping(path = "/update-own-user")
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).UPDATE_OWN_USER.name())")
    public ResponseEntity<AppUserDTO> updateOwnUser(@RequestBody @Valid UpdateOwnAppUserRequestDTO updateOwnAppUserRequestDTO) {
        AppUserDTO appUserDTO = this.appUserDTOUpdateOwnAppUserRequestDTOMapper.mapToAppUserDTO(updateOwnAppUserRequestDTO);

        appUserDTO = this.appUserService.updateOwnUser(appUserDTO);

        return ResponseEntity.ok(appUserDTO);
    }

    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).CREATE_USER.name())")
    @PatchMapping(path = "/add-roles")
    public ResponseEntity<AppUserDTO> addRoles(@RequestParam(name = "username") @NotBlank String username,
                                               @RequestBody @NotNull @NotEmpty List<AppRoleDTO> appRoleDTOList) {
        AppUserDTO appUserDTO = this.appUserService.addAppRolesToAppUser(username, appRoleDTOList);

        return ResponseEntity.ok(appUserDTO);
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).READ_OWN_USER.name())")
    public ResponseEntity<AppUserDTO> findOwnUser() {
        AppUserDTO appUserDTO = this.appUserService.findOwnUser();

        return ResponseEntity.ok(appUserDTO);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).READ_USER.name())")
    public ResponseEntity<AppUserDTO> findById(@PathVariable(name = "id") @NotNull Long id) {
        Optional<AppUserDTO> dtoOptional = this.appUserService.findById(id);

        return ResponseEntity.ok(dtoOptional.orElse(null));
    }

    @GetMapping(path = "/find-all")
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).READ_USER.name())")
    public ResponseEntity<List<AppUserDTO>> findAll() {
        List<AppUserDTO> dtoList = this.appUserService.findAll();

        return ResponseEntity.ok(dtoList);
    }

}

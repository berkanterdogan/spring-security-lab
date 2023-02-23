package com.berkanterdogan.springsecuritylab.controller;

import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.dto.CreateAppAuthorityRequestDTO;
import com.berkanterdogan.springsecuritylab.mapper.AppAuthorityDTOCreateAppAuthorityRequestDTOMapper;
import com.berkanterdogan.springsecuritylab.service.domain.AppAuthorityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/authority")
public class AppAuthorityController {

    private final AppAuthorityService appAuthorityService;

    private final AppAuthorityDTOCreateAppAuthorityRequestDTOMapper appAuthorityDTOCreateAppAuthorityRequestDTOMapper;


    @PostMapping
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).CREATE_AUTHORITY.name())")
    public ResponseEntity<AppAuthorityDTO> create(@RequestBody @Valid CreateAppAuthorityRequestDTO createAppAuthorityRequestDTO) {
        AppAuthorityDTO appAuthorityDTO = this.appAuthorityDTOCreateAppAuthorityRequestDTOMapper.mapToAppAuthorityDTO(createAppAuthorityRequestDTO);

        appAuthorityDTO = this.appAuthorityService.save(appAuthorityDTO);

        return ResponseEntity.ok(appAuthorityDTO);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).READ_AUTHORITY.name())")
    public ResponseEntity<AppAuthorityDTO> findById(@PathVariable(name = "id") @NotNull Long id) {
        Optional<AppAuthorityDTO> dtoOptional = this.appAuthorityService.findById(id);

        return ResponseEntity.ok(dtoOptional.orElse(null));
    }

    @GetMapping(path = "/find-all")
    @PreAuthorize("hasAuthority(T(com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum).READ_AUTHORITY.name())")
    public ResponseEntity<List<AppAuthorityDTO>> findAll() {
        List<AppAuthorityDTO> dtoList = this.appAuthorityService.findAll();

        return ResponseEntity.ok(dtoList);
    }

}

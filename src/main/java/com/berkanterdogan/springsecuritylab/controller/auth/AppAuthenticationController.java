package com.berkanterdogan.springsecuritylab.controller.auth;

import com.berkanterdogan.springsecuritylab.dto.*;
import com.berkanterdogan.springsecuritylab.service.auth.AppAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AppAuthenticationController {

    private final AppAuthenticationService appAuthenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = this.appAuthenticationService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterResponseDto> registerNewCustomerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        RegisterResponseDto registerResponseDto = this.appAuthenticationService.registerNewCustomerUser(registerRequestDto);
        
        return ResponseEntity.ok(registerResponseDto);
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestBody @Valid RefreshRequestDto refreshRequestDto) {
        LoginResponseDto loginResponseDto = this.appAuthenticationService.refreshToken(refreshRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }


}

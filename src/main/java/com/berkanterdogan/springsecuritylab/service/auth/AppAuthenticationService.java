package com.berkanterdogan.springsecuritylab.service.auth;

import com.berkanterdogan.springsecuritylab.dto.*;

public interface AppAuthenticationService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    RegisterResponseDto registerNewCustomerUser(RegisterRequestDto registerRequestDto);

    LoginResponseDto refreshToken(RefreshRequestDto refreshRequestDto);
}

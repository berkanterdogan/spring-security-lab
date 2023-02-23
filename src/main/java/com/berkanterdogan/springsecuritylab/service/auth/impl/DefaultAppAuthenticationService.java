package com.berkanterdogan.springsecuritylab.service.auth.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.berkanterdogan.springsecuritylab.config.property.AppConfigPropertyData;
import com.berkanterdogan.springsecuritylab.dto.*;
import com.berkanterdogan.springsecuritylab.enums.AppRoleEnum;
import com.berkanterdogan.springsecuritylab.exception.RefreshTokenException;
import com.berkanterdogan.springsecuritylab.mapper.AppUserDTORegisterRequestDTOMapper;
import com.berkanterdogan.springsecuritylab.mapper.AppUserDTORegisterResponseDTOMapper;
import com.berkanterdogan.springsecuritylab.service.auth.AppAuthenticationService;
import com.berkanterdogan.springsecuritylab.service.domain.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultAppAuthenticationService implements AppAuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final AppConfigPropertyData appConfigPropertyData;

    private final AppUserService appUserService;

    private final AppUserDTORegisterRequestDTOMapper appUserDTORegisterRequestDTOMapper;

    private final AppUserDTORegisterResponseDTOMapper appUserDTORegisterResponseDTOMapper;

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(), loginRequestDto.getPassword());

        Authentication authentication;

        authentication = this.authenticationManager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();

        String jwtSecret = this.appConfigPropertyData.getJwtSecret();
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        Instant now = Instant.now();

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String username = user.getUsername();
        String accessToken = generateAccessToken(username, algorithm, now, roles);
        String refreshToken = generateRefreshToken(username, algorithm, now, roles, accessToken);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateRefreshToken(String username, Algorithm algorithm, Instant now, List<String> roles, String accessToken) {
        long expirySecondsForRefreshToken = 86400L; // 1 day

        return JWT.create()
                .withIssuer("berkanterdogan.com")
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expirySecondsForRefreshToken))
                .withSubject(username)
                .withClaim("roles", roles)
                .withClaim("accessToken", accessToken)
                .sign(algorithm);
    }

    private String generateAccessToken(String username, Algorithm algorithm, Instant now, List<String> roles) {
        long expirySecondsForAccessToken = 3600L; // 1 hour

        return JWT.create()
                .withIssuer("berkanterdogan.com")
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expirySecondsForAccessToken))
                .withSubject(username)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    @Override
    @Transactional
    public RegisterResponseDto registerNewCustomerUser(RegisterRequestDto registerRequestDto) {
        AppUserDTO appUserDTO = this.appUserDTORegisterRequestDTOMapper.mapToAppUserDTO(registerRequestDto);

        AppRoleDTO appRoleDTO = AppRoleDTO.builder()
                .id(AppRoleEnum.ROLE_CUSTOMER.getId())
                .build();

        appUserDTO.setAppRoleDTOList(Set.of(appRoleDTO));

        appUserDTO = this.appUserService.save(appUserDTO);

        return appUserDTORegisterResponseDTOMapper.mapToAppUserDTO(appUserDTO);
    }

    @Override
    @Transactional
    public LoginResponseDto refreshToken(RefreshRequestDto refreshRequestDto) {
        String accessToken = refreshRequestDto.getAccessToken();
        String refreshToken = refreshRequestDto.getRefreshToken();

        String jwtSecret = this.appConfigPropertyData.getJwtSecret();
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        String username = validateAccessTokenAndRefreshToken(algorithm, accessToken, refreshToken);

        Optional<AppUserDTO> appUserDTOOptional = this.appUserService.findByUsername(username);
        appUserDTOOptional.orElseThrow(() -> new RefreshTokenException("User not found!"));
        AppUserDTO appUserDTO = appUserDTOOptional.get();

        Instant now = Instant.now();

        List<String> roles = new ArrayList<>();
        for (AppRoleDTO appRoleDTO : appUserDTO.getAppRoleDTOList()) {
            roles.add(appRoleDTO.getAppRoleEnum().name());
            for (AppAuthorityDTO appAuthorityDTO : appRoleDTO.getAppAuthorityDTOList()) {
                roles.add(appAuthorityDTO.getAppAuthorityEnum().name());
            }
        }

        String newAccessToken = generateAccessToken(username, algorithm, now, roles);
        String newRefreshToken = generateRefreshToken(username, algorithm, now, roles, newAccessToken);

        return LoginResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private String validateAccessTokenAndRefreshToken(Algorithm algorithm, String accessToken, String refreshToken) {
        JWTVerifier jwtVerifierForRefreshToken = JWT.require(algorithm).build();

        DecodedJWT decodedRefreshTokenJWT = null;
        try {
            decodedRefreshTokenJWT = jwtVerifierForRefreshToken.verify(refreshToken);
        } catch (JWTVerificationException e) {
            throw new RefreshTokenException(e.getMessage(), e);
        }

        Claim accessTokenClaimInRefreshToken = decodedRefreshTokenJWT.getClaim("accessToken");
        if (accessTokenClaimInRefreshToken.isMissing()) {
            throw new RefreshTokenException("accessToken claim is missing in refreshToken field. ");
        }

        String accessTokenInRefreshToken = accessTokenClaimInRefreshToken.asString();
        if (!accessToken.equals(accessTokenInRefreshToken)) {
            throw new RefreshTokenException("accessToken claim does not equal to accessToken field. ");
        }

        validateTokenValues(accessToken, decodedRefreshTokenJWT);

        return decodedRefreshTokenJWT.getSubject();
    }

    private void validateTokenValues(String accessToken, DecodedJWT decodedRefreshTokenJWT) {
        DecodedJWT decodeAccessTokenJWT = JWT.decode(accessToken);
        String accessTokenIssuer = decodeAccessTokenJWT.getIssuer();
        String accessTokenSubject = decodeAccessTokenJWT.getSubject();
        Date accessTokenIssuedAt = decodeAccessTokenJWT.getIssuedAt();
        Claim accessTokenRolesClaim = decodeAccessTokenJWT.getClaim("roles");
        String[] accessTokenRoles = accessTokenRolesClaim.asArray(String.class);

        String refreshTokenIssuer = decodedRefreshTokenJWT.getIssuer();
        String refreshTokenSubject = decodedRefreshTokenJWT.getSubject();
        Date refreshTokenIssuedAt = decodedRefreshTokenJWT.getIssuedAt();
        Claim refreshTokenRolesClaim = decodedRefreshTokenJWT.getClaim("roles");
        String[] refreshTokenRoles = refreshTokenRolesClaim.asArray(String.class);

        if (accessTokenIssuer == null ||
                accessTokenSubject == null ||
                accessTokenIssuedAt == null ||
                accessTokenRoles == null ||
                accessTokenRoles.length == 0 ||
                !accessTokenIssuer.equals(refreshTokenIssuer) ||
                !accessTokenSubject.equals(refreshTokenSubject) ||
                !accessTokenIssuedAt.equals(refreshTokenIssuedAt) ||
                !Arrays.equals(accessTokenRoles, refreshTokenRoles)) {

            throw new RefreshTokenException("Access token and refresh token data are not compatible!");
        }
    }
}

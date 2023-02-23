package com.berkanterdogan.springsecuritylab.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.berkanterdogan.springsecuritylab.config.property.AppConfigPropertyData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class JWTTokenFilter extends OncePerRequestFilter {

    private final AppConfigPropertyData appConfigPropertyData;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get authorization header and validate
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtSecret = this.appConfigPropertyData.getJwtSecret();
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        final String token = authorizationHeader.split(" ")[1].trim();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        // Validate if it is refresh token or not. Refresh token has the access token.
        // If it has the access token, don't allow.
        Claim accessTokenClaim = decodedJWT.getClaim("accessToken");
        if (!accessTokenClaim.isMissing()) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = decodedJWT.getSubject();
        Claim rolesClaim = decodedJWT.getClaim("roles");
        List<SimpleGrantedAuthority> authorityList = Stream.of(rolesClaim.asArray(String.class))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorityList);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

package com.berkanterdogan.springsecuritylab.security.config;

import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.security.filter.JWTTokenFilter;
import com.berkanterdogan.springsecuritylab.service.domain.AppUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

    private final AppUserService appUserService;

    private final JWTTokenFilter jwtTokenFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        // loadUserByUsername
        return username -> {
            Optional<AppUserDTO> appUserDTOOptional = this.appUserService.findByUsername(username);

            appUserDTOOptional.orElseThrow(() -> new UsernameNotFoundException("User not found!"));

            AppUserDTO appUserDTO = appUserDTOOptional.get();

            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            for (AppRoleDTO appRoleDTO : appUserDTO.getAppRoleDTOList()) {
                authorityList.add(new SimpleGrantedAuthority(appRoleDTO.getAppRoleEnum().name()));

                for (AppAuthorityDTO appAuthorityDTO : appRoleDTO.getAppAuthorityDTOList()) {
                    authorityList.add(new SimpleGrantedAuthority(appAuthorityDTO.getAppAuthorityEnum().name()));
                }
            }

            return new User(appUserDTO.getUsername(), appUserDTO.getPassword(), authorityList);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       AuthenticationProvider authenticationProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .cors() // enable CORS
                .and()
                .csrf().disable() // disable CSRF
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                })
                .and()
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

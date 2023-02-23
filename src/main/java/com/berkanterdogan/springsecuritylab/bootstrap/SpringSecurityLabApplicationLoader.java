package com.berkanterdogan.springsecuritylab.bootstrap;

import com.berkanterdogan.springsecuritylab.dto.AppAuthorityDTO;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum;
import com.berkanterdogan.springsecuritylab.enums.AppRoleEnum;
import com.berkanterdogan.springsecuritylab.service.domain.AppAuthorityService;
import com.berkanterdogan.springsecuritylab.service.domain.AppRoleService;
import com.berkanterdogan.springsecuritylab.service.domain.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SpringSecurityLabApplicationLoader implements CommandLineRunner {

    private final AppUserService appUserService;
    private final AppRoleService appRoleService;
    private final AppAuthorityService appAuthorityService;

    @Override
    public void run(String... args) throws Exception {
        // Save CREATE_USER, CREATE_ROLE, CREATE_AUTHORITY, READ_USER, READ_ROLE, and READ_AUTHORITY authorities
        AppAuthorityDTO createUserAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.CREATE_USER)
                .build();
        AppAuthorityDTO updateUserAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.UPDATE_USER)
                .build();
        AppAuthorityDTO updateOwnUserAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.UPDATE_OWN_USER)
                .build();
        AppAuthorityDTO createRoleAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.CREATE_ROLE)
                .build();
        AppAuthorityDTO createAuthorityAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.CREATE_AUTHORITY)
                .build();
        AppAuthorityDTO readUserAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.READ_USER)
                .build();
        AppAuthorityDTO readOwnUserAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.READ_OWN_USER)
                .build();
        AppAuthorityDTO readRoleAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.READ_ROLE)
                .build();
        AppAuthorityDTO readAuthorityAppAuthorityDTO = AppAuthorityDTO.builder()
                .appAuthorityEnum(AppAuthorityEnum.READ_AUTHORITY)
                .build();

        List<AppAuthorityDTO> allAppAuthorityDTOList = this.appAuthorityService.saveAll(
                List.of(createUserAppAuthorityDTO,
                        updateUserAppAuthorityDTO,
                        updateOwnUserAppAuthorityDTO,
                        createRoleAppAuthorityDTO,
                        createAuthorityAppAuthorityDTO,
                        readUserAppAuthorityDTO,
                        readOwnUserAppAuthorityDTO,
                        readRoleAppAuthorityDTO,
                        readAuthorityAppAuthorityDTO)
        );

        // Save ROLE_ADMIN, ROLE_INTERNAL_USER, ROLE_INTERNAL_PRIME_USER, and ROLE_CUSTOMER roles
        AppRoleDTO adminAppRoleDTO = AppRoleDTO.builder()
                .appRoleEnum(AppRoleEnum.ROLE_ADMIN)
                .build();

        AppRoleDTO internalPrimeUserAppRoleDTO = AppRoleDTO.builder()
                .appRoleEnum(AppRoleEnum.ROLE_INTERNAL_PRIME_USER)
                .build();

        AppRoleDTO internalUserAppRoleDTO = AppRoleDTO.builder()
                .appRoleEnum(AppRoleEnum.ROLE_INTERNAL_USER)
                .build();

        AppRoleDTO customerUserAppRoleDTO = AppRoleDTO.builder()
                .appRoleEnum(AppRoleEnum.ROLE_CUSTOMER)
                .build();

        List<AppRoleDTO> appRoleDTOList = this.appRoleService.saveAll(
                List.of(adminAppRoleDTO,
                        internalUserAppRoleDTO,
                        internalPrimeUserAppRoleDTO,
                        customerUserAppRoleDTO)
        );

        // Add CREATE_USER, CREATE_ROLE, CREATE_AUTHORITY, READ_USER, READ_ROLE, and READ_AUTHORITY authorities to the admin role
        adminAppRoleDTO = this.appRoleService.addAppAuthoritiesToAppRole(
                AppRoleEnum.ROLE_ADMIN.name(),
                allAppAuthorityDTOList
        );

        // Add CREATE_USER, READ_USER, READ_ROLE, and READ_AUTHORITY authorities to the internal prime user role
        List<AppAuthorityDTO> internalPrimeUserAuthorityDTOList = allAppAuthorityDTOList.stream()
                .filter(appAuthorityDTO -> {
                    AppAuthorityEnum appAuthorityEnum = appAuthorityDTO.getAppAuthorityEnum();
                    return appAuthorityEnum == AppAuthorityEnum.CREATE_USER ||
                            appAuthorityEnum == AppAuthorityEnum.UPDATE_OWN_USER ||
                            appAuthorityEnum == AppAuthorityEnum.READ_USER ||
                            appAuthorityEnum == AppAuthorityEnum.READ_OWN_USER ||
                            appAuthorityEnum == AppAuthorityEnum.READ_ROLE ||
                            appAuthorityEnum == AppAuthorityEnum.READ_AUTHORITY;
                })
                .collect(Collectors.toList());

        internalPrimeUserAppRoleDTO = this.appRoleService.addAppAuthoritiesToAppRole(
                AppRoleEnum.ROLE_INTERNAL_PRIME_USER.name(),
                internalPrimeUserAuthorityDTOList
        );

        // Add READ_USER, READ_ROLE, and READ_AUTHORITY authorities to the internal user role
        List<AppAuthorityDTO> internalUserAuthorityDTOList = allAppAuthorityDTOList.stream()
                .filter(appAuthorityDTO -> {
                    AppAuthorityEnum appAuthorityEnum = appAuthorityDTO.getAppAuthorityEnum();
                    return appAuthorityEnum == AppAuthorityEnum.UPDATE_OWN_USER ||
                            appAuthorityEnum == AppAuthorityEnum.READ_USER ||
                            appAuthorityEnum == AppAuthorityEnum.READ_OWN_USER ||
                            appAuthorityEnum == AppAuthorityEnum.READ_ROLE ||
                            appAuthorityEnum == AppAuthorityEnum.READ_AUTHORITY;
                })
                .collect(Collectors.toList());

        internalUserAppRoleDTO = this.appRoleService.addAppAuthoritiesToAppRole(
                AppRoleEnum.ROLE_INTERNAL_USER.name(),
                internalUserAuthorityDTOList
        );

        // Add UPDATE_OWN_USER and READ_OWN_USER authorities to the customer role
        List<AppAuthorityDTO> customerUserAuthorityDTOList = allAppAuthorityDTOList.stream()
                .filter(appAuthorityDTO -> {
                    AppAuthorityEnum appAuthorityEnum = appAuthorityDTO.getAppAuthorityEnum();
                    return appAuthorityEnum == AppAuthorityEnum.UPDATE_OWN_USER ||
                            appAuthorityEnum == AppAuthorityEnum.READ_OWN_USER;
                })
                .collect(Collectors.toList());

        customerUserAppRoleDTO = this.appRoleService.addAppAuthoritiesToAppRole(
                AppRoleEnum.ROLE_CUSTOMER.name(),
                customerUserAuthorityDTOList
        );

        // Create an admin, internal prime and internal users
        AppUserDTO adminUserDTO = AppUserDTO.builder()
                .username("john.johnson")
                .password("pwd12340")
                .firstname("John")
                .lastname("Johnson")
                .build();

        AppUserDTO internalPrimeUserDTO = AppUserDTO.builder()
                .username("emma.johnson")
                .password("pwd12341")
                .firstname("Emma")
                .lastname("Johnson")
                .build();

        AppUserDTO internalUserDTO = AppUserDTO.builder()
                .username("joe.johnson")
                .password("pwd12342")
                .firstname("Joe")
                .lastname("Johnson")
                .build();

        List<AppUserDTO> appUserDTOList = this.appUserService.saveAll(
                List.of(adminUserDTO,
                        internalPrimeUserDTO,
                        internalUserDTO)
        );

        // Add ROLE_ADMIN role to the admin user
        List<AppRoleDTO> adminRoles = appRoleDTOList.stream()
                .filter(appRoleDTO -> appRoleDTO.getAppRoleEnum() == AppRoleEnum.ROLE_ADMIN)
                .collect(Collectors.toList());

        adminUserDTO = this.appUserService.addAppRolesToAppUser("john.johnson", adminRoles);

        // Add ROLE_INTERNAL_PRIME_USER role to the internal prime user
        List<AppRoleDTO> internalPrimeUserRoles = appRoleDTOList.stream()
                .filter(appRoleDTO -> appRoleDTO.getAppRoleEnum() == AppRoleEnum.ROLE_INTERNAL_PRIME_USER)
                .collect(Collectors.toList());

        internalUserDTO = this.appUserService.addAppRolesToAppUser("emma.johnson", internalPrimeUserRoles);

        // Add ROLE_INTERNAL_USER role to the internal user
        List<AppRoleDTO> internalUserRoles = appRoleDTOList.stream()
                .filter(appRoleDTO -> appRoleDTO.getAppRoleEnum() == AppRoleEnum.ROLE_INTERNAL_USER)
                .collect(Collectors.toList());

        internalUserDTO = this.appUserService.addAppRolesToAppUser("joe.johnson", internalUserRoles);


    }
}

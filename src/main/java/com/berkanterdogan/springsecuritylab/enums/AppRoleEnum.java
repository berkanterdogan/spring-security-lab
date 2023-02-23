package com.berkanterdogan.springsecuritylab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AppRoleEnum {

    ROLE_ADMIN(1L),
    ROLE_INTERNAL_USER(2L),
    ROLE_INTERNAL_PRIME_USER(3L),
    ROLE_CUSTOMER(4L);

    private final long id;

    public static AppRoleEnum getEnumByName(String enumName) {
        for (AppRoleEnum appRoleEnum : AppRoleEnum.values()) {
            if (appRoleEnum.name().equals(enumName)) {
                return appRoleEnum;
            }
        }

        return null;
    }
}

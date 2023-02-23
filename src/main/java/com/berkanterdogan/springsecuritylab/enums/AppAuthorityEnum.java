package com.berkanterdogan.springsecuritylab.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AppAuthorityEnum {

    CREATE_USER(1L),
    UPDATE_USER(2L),
    UPDATE_OWN_USER(3L),
    CREATE_ROLE(4L),
    CREATE_AUTHORITY(5L),
    READ_USER(6L),
    READ_OWN_USER(7L),
    READ_ROLE(8L),
    READ_AUTHORITY(9L);

    private final long id;

    public static AppAuthorityEnum getEnumByName(String enumName) {
        for (AppAuthorityEnum appAuthorityEnum : AppAuthorityEnum.values()) {
            if (appAuthorityEnum.name().equals(enumName)) {
                return appAuthorityEnum;
            }
        }

        return null;
    }
}

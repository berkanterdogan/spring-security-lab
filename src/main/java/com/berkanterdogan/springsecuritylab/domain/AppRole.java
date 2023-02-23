package com.berkanterdogan.springsecuritylab.domain;

import com.berkanterdogan.springsecuritylab.domain.base.BaseEntity;
import com.berkanterdogan.springsecuritylab.enums.AppRoleEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AppRole extends BaseEntity {

    @Column(name = "name")
    @Enumerated(value = EnumType.STRING)
    private AppRoleEnum appRoleEnum;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppAuthority> appAuthorityList = new HashSet<>();

    @Builder
    public AppRole(Long id, AppRoleEnum appRoleEnum, Set<AppAuthority> appAuthorityList) {
        super(id);
        this.appRoleEnum = appRoleEnum;
        this.appAuthorityList = appAuthorityList;
    }
}

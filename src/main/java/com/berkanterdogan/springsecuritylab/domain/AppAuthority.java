package com.berkanterdogan.springsecuritylab.domain;

import com.berkanterdogan.springsecuritylab.domain.base.BaseEntity;
import com.berkanterdogan.springsecuritylab.enums.AppAuthorityEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AppAuthority extends BaseEntity {

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private AppAuthorityEnum appAuthorityEnum;

    @Builder
    public AppAuthority(Long id, AppAuthorityEnum appAuthorityEnum) {
        super(id);
        this.appAuthorityEnum = appAuthorityEnum;
    }
}

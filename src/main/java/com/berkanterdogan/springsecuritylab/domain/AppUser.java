package com.berkanterdogan.springsecuritylab.domain;

import com.berkanterdogan.springsecuritylab.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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
public class AppUser extends BaseEntity {

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppRole> appRoleList = new HashSet<>();

    @Builder
    public AppUser(Long id, String username, String password, String firstname, String lastname, Set<AppRole> appRoleList) {
        super(id);
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.appRoleList = appRoleList;
    }
}

package com.berkanterdogan.springsecuritylab.repository;

import com.berkanterdogan.springsecuritylab.domain.AppRole;
import com.berkanterdogan.springsecuritylab.enums.AppRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    Optional<AppRole> findAppRoleByAppRoleEnum(AppRoleEnum appRoleEnum);
}

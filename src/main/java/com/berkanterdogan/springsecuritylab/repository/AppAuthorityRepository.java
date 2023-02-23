package com.berkanterdogan.springsecuritylab.repository;

import com.berkanterdogan.springsecuritylab.domain.AppAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAuthorityRepository extends JpaRepository<AppAuthority, Long> {
}

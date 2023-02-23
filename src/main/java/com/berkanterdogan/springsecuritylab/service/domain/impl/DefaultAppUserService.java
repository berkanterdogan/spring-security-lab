package com.berkanterdogan.springsecuritylab.service.domain.impl;

import com.berkanterdogan.springsecuritylab.domain.AppUser;
import com.berkanterdogan.springsecuritylab.dto.AppRoleDTO;
import com.berkanterdogan.springsecuritylab.dto.AppUserDTO;
import com.berkanterdogan.springsecuritylab.mapper.AppUserMapper;
import com.berkanterdogan.springsecuritylab.mapper.base.BaseEntityDtoMapper;
import com.berkanterdogan.springsecuritylab.repository.AppUserRepository;
import com.berkanterdogan.springsecuritylab.service.domain.AppUserService;
import com.berkanterdogan.springsecuritylab.service.domain.base.impl.DefaultBaseDomainService;
import com.berkanterdogan.springsecuritylab.util.spring.SpringBeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultAppUserService extends DefaultBaseDomainService<AppUser, AppUserDTO, Long> implements AppUserService {

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;

    @Override
    @Transactional
    public AppUserDTO save(AppUserDTO appUserDTO) {
        PasswordEncoder passwordEncoder = SpringBeanUtil.getBean(PasswordEncoder.class);
        String encodedPassword = passwordEncoder.encode(appUserDTO.getPassword());
        appUserDTO.setPassword(encodedPassword);

        return super.save(appUserDTO);
    }

    @Override
    @Transactional
    public List<AppUserDTO> saveAll(List<AppUserDTO> appUserDTOList) {
        appUserDTOList.forEach(appUserDTO -> {
            PasswordEncoder passwordEncoder = SpringBeanUtil.getBean(PasswordEncoder.class);
            String encodedPassword = passwordEncoder.encode(appUserDTO.getPassword());
            appUserDTO.setPassword(encodedPassword);
        });

        return super.saveAll(appUserDTOList);
    }

    @Override
    @Transactional
    public AppUserDTO updateOwnUser(AppUserDTO appUserDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        Optional<AppUser> appUserOptional = this.appUserRepository.findAppUserByUsername(username);

        AppUser appUser = appUserOptional.get();
        appUser.setUsername(appUserDTO.getUsername());
        PasswordEncoder passwordEncoder = SpringBeanUtil.getBean(PasswordEncoder.class);
        String encodedPassword = passwordEncoder.encode(appUserDTO.getPassword());
        appUser.setPassword(encodedPassword);
        appUser.setFirstname(appUser.getFirstname());
        appUser.setLastname(appUser.getLastname());

        appUserDTO = this.appUserMapper.mapToDTO(appUser);

        return super.save(appUserDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUserDTO> findByUsername(String username) {
        Optional<AppUser> appUserOptional = this.appUserRepository.findAppUserByUsername(username);
        AppUser appUser = appUserOptional.orElse(null);

        return Optional.ofNullable(this.appUserMapper.mapToDTO(appUser));
    }

    @Override
    @Transactional(readOnly = true)
    public AppUserDTO findOwnUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        Optional<AppUser> appUserOptional = this.appUserRepository.findAppUserByUsername(username);
        AppUser appUser = appUserOptional.orElse(null);

        return this.appUserMapper.mapToDTO(appUser);
    }

    @Override
    @Transactional
    public AppUserDTO addAppRolesToAppUser(String username, List<AppRoleDTO> appRoleDTOList) {
        Optional<AppUser> appUserOptional = this.appUserRepository.findAppUserByUsername(username);
        AppUserDTO appUserDTO = null;
        if (appUserOptional.isPresent()) {
            appUserDTO = this.appUserMapper.mapToDTO(appUserOptional.get());
            appUserDTO.getAppRoleDTOList().addAll(appRoleDTOList);
            appUserDTO = super.save(appUserDTO);
        }

        return appUserDTO;
    }

    @Override
    public JpaRepository<AppUser, Long> getRepository() {
        return this.appUserRepository;
    }

    @Override
    public BaseEntityDtoMapper<AppUser, AppUserDTO> getMapper() {
        return this.appUserMapper;
    }
}

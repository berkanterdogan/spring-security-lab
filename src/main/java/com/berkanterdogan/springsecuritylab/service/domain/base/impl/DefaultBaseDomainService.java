package com.berkanterdogan.springsecuritylab.service.domain.base.impl;


import com.berkanterdogan.springsecuritylab.domain.base.BaseEntity;
import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;
import com.berkanterdogan.springsecuritylab.mapper.base.BaseEntityDtoMapper;
import com.berkanterdogan.springsecuritylab.service.domain.base.BaseDomainService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class DefaultBaseDomainService<E extends BaseEntity, D extends BaseDTO, ID extends Serializable> implements BaseDomainService<E, D, ID> {

    public abstract JpaRepository<E, ID> getRepository();

    public abstract BaseEntityDtoMapper<E, D> getMapper();

    @Override
    @Transactional
    public D save(D dto) {
        BaseEntityDtoMapper<E, D> mapper = getMapper();
        E entity = mapper.mapToEntity(dto);

        JpaRepository<E, ID> repository = getRepository();
        entity = repository.save(entity);

        return mapper.mapToDTO(entity);
    }

    @Override
    @Transactional
    public List<D> saveAll(List<D> dtoList) {
        BaseEntityDtoMapper<E, D> mapper = getMapper();
        List<E> entityList = mapper.mapToEntityList(dtoList);

        JpaRepository<E, ID> repository = getRepository();
        entityList = repository.saveAll(entityList);

        return mapper.mapToDTOList(entityList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<D> findById(ID id) {
        JpaRepository<E, ID> repository = getRepository();
        Optional<E> entityOptional = repository.findById(id);
        E entity = entityOptional.orElse(null);

        BaseEntityDtoMapper<E, D> mapper = getMapper();
        D dto = mapper.mapToDTO(entity);

        return Optional.ofNullable(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> findAll() {
        JpaRepository<E, ID> repository = getRepository();
        List<E> entityList = repository.findAll();

        BaseEntityDtoMapper<E, D> mapper = getMapper();

        return mapper.mapToDTOList(entityList);
    }
}

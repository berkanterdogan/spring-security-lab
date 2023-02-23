package com.berkanterdogan.springsecuritylab.service.domain.base;

import com.berkanterdogan.springsecuritylab.domain.base.BaseEntity;
import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseDomainService<E extends BaseEntity, D extends BaseDTO, ID extends Serializable> {

    D save(D dto);

    List<D> saveAll(List<D> dtoList);

    Optional<D> findById(ID id);

    List<D> findAll();
}

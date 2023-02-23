package com.berkanterdogan.springsecuritylab.mapper.base;

import com.berkanterdogan.springsecuritylab.domain.base.BaseEntity;
import com.berkanterdogan.springsecuritylab.dto.base.BaseDTO;

import java.util.List;

public interface BaseEntityDtoMapper<E extends BaseEntity, D extends BaseDTO>{

    D mapToDTO(E entity);

    E mapToEntity(D dto);

    List<D> mapToDTOList(List<E> entityList);

    List<E> mapToEntityList(List<D> dtoList);
}

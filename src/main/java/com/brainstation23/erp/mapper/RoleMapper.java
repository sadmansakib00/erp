package com.brainstation23.erp.mapper;

import com.brainstation23.erp.model.domain.Role;
import com.brainstation23.erp.model.dto.RoleResponse;
import com.brainstation23.erp.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role entityToDomain(RoleEntity entity);

    RoleResponse domainToResponse(Role role);
}

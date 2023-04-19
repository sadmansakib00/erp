package com.brainstation23.erp.service;

import com.brainstation23.erp.exception.custom.custom.NotFoundException;
import com.brainstation23.erp.mapper.RoleMapper;
import com.brainstation23.erp.model.domain.Role;
import com.brainstation23.erp.model.dto.CreateRoleRequest;
import com.brainstation23.erp.persistence.entity.RoleEntity;
import com.brainstation23.erp.persistence.entity.UserEntity;
import com.brainstation23.erp.persistence.repository.RoleRepository;
import com.brainstation23.erp.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

    private static final String ROLE_NOT_FOUND = "Role Not Found";
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    private final UserRepository userRepository;

    public Page<Role> getAll(Pageable pageable) {
        var entities = roleRepository.findAll(pageable);
        return entities.map(roleMapper::entityToDomain);
    }

    public Role getOne(Integer id) {
        var entity = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
        return roleMapper.entityToDomain(entity);
    }

    public Integer createOne(CreateRoleRequest createRoleRequest) {
        var entity = new RoleEntity();
        entity.setId(Integer.valueOf(createRoleRequest.getId()))
                .setName(createRoleRequest.getName());
        var createdEntity = roleRepository.save(entity);
        return createdEntity.getId();
    }

    public void assignUserRole(UUID userId, Integer roleId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        RoleEntity role = roleRepository.findById(roleId).orElse(null);

        Set<RoleEntity> userRoles = user.getRoles();

        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    public void unassignUserRole(UUID userId, Integer roleId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        Set<RoleEntity> userRoles = user.getRoles();

        userRoles.removeIf(x -> x.getId() == roleId);
        userRepository.save(user);
    }

    public Set<RoleEntity> getUserRole(UserEntity userEntity) {
        return userEntity.getRoles();
    }

    public Set<RoleEntity> getUserNotRoles(UserEntity user) {
        return roleRepository.getUserNotRoles(user.getId());
    }

}

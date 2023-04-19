package com.brainstation23.erp.persistence.repository;

import com.brainstation23.erp.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Query(
            value = "SELECT * FROM roles WHERE id NOT IN (SELECT role_id FROM user_role WHERE user_id = ?1)",
            nativeQuery = true
    )
    Set<RoleEntity> getUserNotRoles(String userId);

}

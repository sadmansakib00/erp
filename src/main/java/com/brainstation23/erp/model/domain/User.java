package com.brainstation23.erp.model.domain;


import com.brainstation23.erp.persistence.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private double accountBalance;
    private String role;
    private String password;

    Set<RoleEntity> roles = new HashSet<>();
}

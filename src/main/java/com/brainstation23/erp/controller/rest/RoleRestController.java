package com.brainstation23.erp.controller.rest;

import com.brainstation23.erp.mapper.RoleMapper;
import com.brainstation23.erp.model.dto.CreateRoleRequest;
import com.brainstation23.erp.model.dto.RoleResponse;
import com.brainstation23.erp.persistence.entity.RoleEntity;
import com.brainstation23.erp.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@Tag(name = "Role")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/roles")
public class RoleRestController {
     private final RoleService roleService;
     private final RoleMapper roleMapper;

     @Operation(summary = "Get All Roles")
     @GetMapping
     public ResponseEntity<Page<RoleResponse>> getAll(@ParameterObject Pageable pageable) {
         log.info(("Getting List of Roles"));
        var domains = roleService.getAll(pageable);
        return ResponseEntity.ok(domains.map(roleMapper::domainToResponse));
     }


    @Operation(summary = "Get Single Role")
    @GetMapping("{id}")
    public ResponseEntity<RoleResponse> getOne(@PathVariable Integer id) {
        log.info("Getting Details of Role({})", id);
        var domain = roleService.getOne(id);
        return ResponseEntity.ok(roleMapper.domainToResponse(domain));
    }

    @Operation(summary = "Create Single Role")
    @PostMapping
    public ResponseEntity<Void> createOne(@RequestBody @Valid CreateRoleRequest createRequest) {
        log.info("Creating an Role: {} ", createRequest);
        var id = roleService.createOne(createRequest);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Delete Single Role")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable Integer id) {
        log.info("Deleting an Role({}) ", id);
        roleService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assigning role to an user")
    @PostMapping("/assignARole/{userId}/{roleId}")
    public ResponseEntity<Void> assignUserRole(@PathVariable UUID userId, @PathVariable Integer roleId) {
         log.info("Assigning role to user");
         roleService.assignUserRole(userId, roleId);
         return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Removing a role from an user")
    @PostMapping("/unassignARole/{userId}/{roleId}")
    public ResponseEntity<Void> unassignUserRole(@PathVariable UUID userId, @PathVariable Integer roleId) {
         log.info("Removing a role from an user");
         roleService.unassignUserRole(userId, roleId);
         return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get roles of an user")
    @PostMapping("/user/{userId}")
    public ResponseEntity<Set<RoleEntity>> getUserRole(@PathVariable UUID userId) {
         log.info("Getting the roles given user have");
         var result = roleService.getUserRole(userId);
        return ResponseEntity.ok(result);
     }

     @Operation(summary = "Get roles an user doesn't have")
    @PostMapping("/missing/user/{userId}")
    public ResponseEntity<Set<RoleEntity>> getUserMissingRoles(@PathVariable UUID userId) {
         log.info("Getting the roles given user doesn't have");
         var result = roleService.getUserMissingRoles(userId);
         return ResponseEntity.ok(result);
     }
}

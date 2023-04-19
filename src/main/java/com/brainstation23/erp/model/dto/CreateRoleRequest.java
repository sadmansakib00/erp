package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
public class CreateRoleRequest {

    @NotNull
    @Schema(description = "Role id", example = "1")
    private String id;

    @NotNull
    @Schema(description = "Role name", example = "ADMIN")
    private String name;
}

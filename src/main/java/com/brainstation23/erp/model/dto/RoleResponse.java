package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RoleResponse {

    @Schema(description = "Role ID", example = "1")
    private Integer id;

    @Schema(description = "Role name", example = "ADMIN")
    private String name;

}

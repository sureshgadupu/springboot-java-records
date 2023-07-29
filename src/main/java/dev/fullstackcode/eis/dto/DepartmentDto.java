package dev.fullstackcode.eis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record DepartmentDto(@NotNull Integer id, @NotBlank String dept_name) implements Serializable {
}

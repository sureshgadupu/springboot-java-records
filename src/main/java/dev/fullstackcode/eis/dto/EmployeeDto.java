package dev.fullstackcode.eis.dto;


import dev.fullstackcode.eis.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

public record EmployeeDto(Integer id, @NotBlank(message = "FirstName should not be blank") String first_name,
                          @NotBlank String last_name, Gender gender,
                          @PastOrPresent LocalDate birth_date, LocalDate hire_date,
                          DepartmentDto department) implements Serializable {
}

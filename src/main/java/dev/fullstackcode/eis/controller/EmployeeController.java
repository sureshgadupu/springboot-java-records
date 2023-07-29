package dev.fullstackcode.eis.controller;


import dev.fullstackcode.eis.config.AppProperties;
import dev.fullstackcode.eis.dto.DepartmentDto;
import dev.fullstackcode.eis.dto.EmployeeDto;
import dev.fullstackcode.eis.entity.Department;
import dev.fullstackcode.eis.entity.Employee;
import dev.fullstackcode.eis.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {


    private final EmployeeService employeeService;
    private final AppProperties appProperties;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AppProperties appProperties) {
        this.employeeService = employeeService;
        this.appProperties = appProperties;
    }


    @GetMapping("/properties")
    public String displayProperties() {
        return "Property One: " + appProperties.propertyOne()
                + ", Property Two: " + appProperties.propertyTwo();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable @Positive Integer id) {

        Employee employeeEntity = employeeService.getEmployeeById(id);
        return mapEntityToDto(employeeEntity);
    }

    @ResponseStatus(HttpStatus.CREATED) // send HTTP 201 instead of 200 as new object    // created
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        Employee empEntity = mapDtoToEntity(employeeDto);
        Employee employeeEntity = employeeService.createEmployee(empEntity);
        return mapEntityToDto(employeeEntity);

    }

    private Employee mapDtoToEntity(EmployeeDto employeeDto) {
        Employee employeeEntity = new Employee();
        employeeEntity.setId(employeeDto.id());
        employeeEntity.setGender(employeeDto.gender());
        employeeEntity.setFirst_name(employeeDto.first_name());
        employeeEntity.setLast_name(employeeDto.last_name());
        employeeEntity.setHire_date(employeeDto.hire_date());
        employeeEntity.setBirth_date(employeeDto.birth_date());
        if (employeeDto.department() != null) {
            Department dept = new Department();
            dept.setName(employeeDto.department().dept_name());
            dept.setId(employeeDto.department().id());
            employeeEntity.setDepartment(dept);
        }

        return employeeEntity;
    }

    private EmployeeDto mapEntityToDto(Employee employee) {

        return new EmployeeDto(employee.getId(), employee.getFirst_name(), employee.getLast_name(), employee.getGender(), employee.getBirth_date(), employee.getHire_date(),
                employee.getDepartment() != null ? new DepartmentDto(employee.getDepartment().getId(), employee.getDepartment().getName()) : null);
    }

}

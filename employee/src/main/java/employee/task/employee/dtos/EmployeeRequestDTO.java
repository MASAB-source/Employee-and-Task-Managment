package employee.task.employee.dtos;

import employee.task.employee.domain.Role;

public record EmployeeRequestDTO(
    String fullName,
    String email,
    String password,
    Role role
) {}
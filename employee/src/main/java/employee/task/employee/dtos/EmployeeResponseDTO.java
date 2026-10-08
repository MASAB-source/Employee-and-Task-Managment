package employee.task.employee.dtos;

public record EmployeeResponseDTO(
    Long id,
    String fullName,
    String email,
    String role
) {}
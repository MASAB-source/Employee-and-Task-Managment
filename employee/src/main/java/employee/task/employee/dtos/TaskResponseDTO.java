package employee.task.employee.dtos;

import employee.task.employee.domain.TaskStatus;

public record TaskResponseDTO(
    Long id,
    String title,
    String description,
    TaskStatus status,
    EmployeeResponseDTO assignedEmployee
) {}
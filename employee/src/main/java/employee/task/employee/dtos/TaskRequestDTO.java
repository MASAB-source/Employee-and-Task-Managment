package employee.task.employee.dtos;

import employee.task.employee.domain.TaskStatus;

public record TaskRequestDTO(
    Long assignedEmployeeId,
    String title,
    String description,
    TaskStatus status
) {}
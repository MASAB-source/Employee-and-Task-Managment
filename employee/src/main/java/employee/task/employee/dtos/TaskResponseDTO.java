package employee.task.employee.dtos;

import employee.task.employee.domain.TaskStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private EmployeeResponseDTO assignedEmployee;
}
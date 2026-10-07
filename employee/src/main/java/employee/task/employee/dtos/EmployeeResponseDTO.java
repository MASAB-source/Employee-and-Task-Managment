package employee.task.employee.dtos;

import employee.task.employee.domain.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
}
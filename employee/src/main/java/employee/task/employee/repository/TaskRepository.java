package employee.task.employee.repository;

import employee.task.employee.domain.Task;
import employee.task.employee.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find all tasks assigned to a specific employee ID
    List<Task> findByAssignedEmployeeId(Long employeeId);

    // Find all tasks filtered by status (PENDING, IN_PROGRESS, COMPLETED)
    List<Task> findByStatus(TaskStatus status);
}
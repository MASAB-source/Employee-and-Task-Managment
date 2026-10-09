package employee.task.employee.service;

import employee.task.employee.dtos.PagedResponseDTO;
import employee.task.employee.dtos.TaskRequestDTO;
import employee.task.employee.dtos.TaskResponseDTO;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO requestDTO);
    TaskResponseDTO getTaskById(Long id);
    
    // Paginated & Searchable Get All
    PagedResponseDTO<TaskResponseDTO> getAllTasks(int page, int size, String sortBy, String sortDir, String search);

    // Paginated Get Tasks By Employee
    PagedResponseDTO<TaskResponseDTO> getTasksByEmployeeId(Long employeeId, int page, int size, String sortBy, String sortDir);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO);
    void deleteTask(Long id);
}
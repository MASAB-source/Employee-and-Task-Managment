package employee.task.employee.service;

import employee.task.employee.dtos.TaskRequestDTO;
import employee.task.employee.dtos.TaskResponseDTO;

import java.util.List;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO requestDTO);
    TaskResponseDTO getTaskById(Long id);
    List<TaskResponseDTO> getAllTasks();
    List<TaskResponseDTO> getTasksByEmployeeId(Long employeeId);
    TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO);
    void deleteTask(Long id);
}
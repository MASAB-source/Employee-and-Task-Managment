package employee.task.employee.service.impl;

import employee.task.employee.domain.Employee;
import employee.task.employee.domain.Task;
import employee.task.employee.domain.TaskStatus;
import employee.task.employee.dtos.EmployeeResponseDTO;
import employee.task.employee.dtos.TaskRequestDTO;
import employee.task.employee.dtos.TaskResponseDTO;
import employee.task.employee.exception.ResourceNotFoundException;
import employee.task.employee.repository.EmployeeRepository;
import employee.task.employee.repository.TaskRepository;
import employee.task.employee.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Employee assignedEmployee = null;
      
        if (requestDTO.assignedEmployeeId() != null) {
            assignedEmployee = employeeRepository.findById(requestDTO.assignedEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + requestDTO.assignedEmployeeId()));
        }

        Task task = Task.builder()
                .title(requestDTO.title())
                .description(requestDTO.description())
                .status(requestDTO.status())
                .assignedEmployee(assignedEmployee)
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToResponseDTO(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return mapToResponseDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponseDTO> responseDTOs = new ArrayList<>();

        for (Task task : tasks) {
            responseDTOs.add(mapToResponseDTO(task));
        }

        return responseDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByEmployeeId(Long employeeId) {
        List<Task> tasks = taskRepository.findByAssignedEmployeeId(employeeId);
        List<TaskResponseDTO> responseDTOs = new ArrayList<>();

        for (Task task : tasks) {
            responseDTOs.add(mapToResponseDTO(task));
        }

        return responseDTOs;
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (requestDTO.assignedEmployeeId() != null) {
            Employee assignedEmployee = employeeRepository.findById(requestDTO.assignedEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + requestDTO.assignedEmployeeId()));
            task.setAssignedEmployee(assignedEmployee);
        } else {
            task.setAssignedEmployee(null);
        }

        task.setTitle(requestDTO.title());
        task.setDescription(requestDTO.description());
        task.setStatus(requestDTO.status());

        Task updatedTask = taskRepository.save(task);
        return mapToResponseDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

   private TaskResponseDTO mapToResponseDTO(Task task) {
    EmployeeResponseDTO employeeDTO = null;
    if (task.getAssignedEmployee() != null) {
        Employee emp = task.getAssignedEmployee();
        String roleStr = emp.getRole() != null ? emp.getRole().name() : null;

        employeeDTO = new EmployeeResponseDTO(
                emp.getId(),
                emp.getFullName(),
                emp.getEmail(),
                roleStr
        );
    }

    String statusStr = task.getStatus() != null ? task.getStatus().name() : null;

   return new TaskResponseDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getStatus(),
        employeeDTO
);
}
}
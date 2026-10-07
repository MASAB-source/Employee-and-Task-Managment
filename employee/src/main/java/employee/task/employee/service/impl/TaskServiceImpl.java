package employee.task.employee.service.impl;

import employee.task.employee.domain.Employee;
import employee.task.employee.domain.Task;
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
        if (requestDTO.getAssignedEmployeeId() != null) {
            assignedEmployee = employeeRepository.findById(requestDTO.getAssignedEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + requestDTO.getAssignedEmployeeId()));
        }

        Task task = Task.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .status(requestDTO.getStatus())
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
            TaskResponseDTO dto = mapToResponseDTO(task);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByEmployeeId(Long employeeId) {
        List<Task> tasks = taskRepository.findByAssignedEmployeeId(employeeId);
        List<TaskResponseDTO> responseDTOs = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponseDTO dto = mapToResponseDTO(task);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (requestDTO.getAssignedEmployeeId() != null) {
            Employee assignedEmployee = employeeRepository.findById(requestDTO.getAssignedEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + requestDTO.getAssignedEmployeeId()));
            task.setAssignedEmployee(assignedEmployee);
        } else {
            task.setAssignedEmployee(null);
        }

        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setStatus(requestDTO.getStatus());

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
            employeeDTO = EmployeeResponseDTO.builder()
                    .id(task.getAssignedEmployee().getId())
                    .fullName(task.getAssignedEmployee().getFullName())
                    .email(task.getAssignedEmployee().getEmail())
                    .role(task.getAssignedEmployee().getRole())
                    .build();
        }

        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .assignedEmployee(employeeDTO)
                .build();
    }
}
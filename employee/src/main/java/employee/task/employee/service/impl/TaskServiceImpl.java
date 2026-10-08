package employee.task.employee.service.impl;

import employee.task.employee.domain.Employee;
import employee.task.employee.domain.Task;
import employee.task.employee.dtos.EmployeeResponseDTO;
import employee.task.employee.dtos.PagedResponseDTO;
import employee.task.employee.dtos.TaskRequestDTO;
import employee.task.employee.dtos.TaskResponseDTO;
import employee.task.employee.exception.ResourceNotFoundException;
import employee.task.employee.repository.EmployeeRepository;
import employee.task.employee.repository.TaskRepository;
import employee.task.employee.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PagedResponseDTO<TaskResponseDTO> getAllTasks(int page, int size, String sortBy, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Task> taskPage = taskRepository.searchTasks(search, pageable);

        return mapToPagedResponse(taskPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDTO<TaskResponseDTO> getTasksByEmployeeId(Long employeeId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Task> taskPage = taskRepository.findByAssignedEmployeeId(employeeId, pageable);

        return mapToPagedResponse(taskPage);
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

        if (requestDTO.status() != null) {
            task.setStatus(requestDTO.status());
        }

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
            employeeDTO = new EmployeeResponseDTO(
                    emp.getId(),
                    emp.getFullName(),
                    emp.getEmail(),
                    emp.getRole().name()
            );
        }

        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                employeeDTO
        );
    }

    private PagedResponseDTO<TaskResponseDTO> mapToPagedResponse(Page<Task> taskPage) {
        List<TaskResponseDTO> content = taskPage.getContent().stream()
                .map(this::mapToResponseDTO)
                .toList();

        return new PagedResponseDTO<>(
                content,
                taskPage.getNumber(),
                taskPage.getSize(),
                taskPage.getTotalElements(),
                taskPage.getTotalPages(),
                taskPage.isLast()
        );
    }
}
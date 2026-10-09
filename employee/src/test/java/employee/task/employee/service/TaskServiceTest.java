package employee.task.employee.service;

import employee.task.employee.domain.Employee;
import employee.task.employee.domain.Role;
import employee.task.employee.domain.Task;
import employee.task.employee.domain.TaskStatus;
import employee.task.employee.dtos.PagedResponseDTO;
import employee.task.employee.dtos.TaskRequestDTO;
import employee.task.employee.dtos.TaskResponseDTO;
import employee.task.employee.exception.ResourceNotFoundException;
import employee.task.employee.repository.EmployeeRepository;
import employee.task.employee.repository.TaskRepository;
import employee.task.employee.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Employee employee;
    private Task task;
    private TaskRequestDTO taskRequestDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john@example.com")
                .role(Role.ROLE_EMPLOYEE)
                .build();

        task = Task.builder()
                .id(10L)
                .title("Database Setup")
                .description("Configure PostgreSQL schema")
                .status(TaskStatus.PENDING)
                .assignedEmployee(employee)
                .build();

        taskRequestDTO = new TaskRequestDTO(1L, "Database Setup", "Configure PostgreSQL schema", TaskStatus.PENDING);
    }

    @Test
    void test8_CreateTask_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO result = taskService.createTask(taskRequestDTO);

        assertNotNull(result);
        assertEquals("Database Setup", result.title());
        assertEquals(TaskStatus.PENDING, result.status());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void test9_CreateTask_EmployeeNotFound_ThrowsException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        TaskRequestDTO invalidDTO = new TaskRequestDTO(99L, "Task", "Desc", TaskStatus.PENDING);

        assertThrows(ResourceNotFoundException.class, () -> taskService.createTask(invalidDTO));
    }

    @Test
    void test10_GetTaskById_Success() {
        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));

        TaskResponseDTO result = taskService.getTaskById(10L);

        assertNotNull(result);
        assertEquals(10L, result.id());
    }

    @Test
    void test11_GetTaskById_NotFound_ThrowsException() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(999L));
    }

    @Test
    void test12_GetAllTasks_Success() {
        Page<Task> taskPage = new PageImpl<>(List.of(task));
        when(taskRepository.searchTasks(any(), any(Pageable.class))).thenReturn(taskPage);

        PagedResponseDTO<TaskResponseDTO> results = taskService.getAllTasks(0, 10, "id", "asc", null);

        assertNotNull(results);
        assertFalse(results.content().isEmpty());
        assertEquals(1, results.content().size());
    }

    @Test
    void test13_GetTasksByEmployeeId_Success() {
        Page<Task> taskPage = new PageImpl<>(List.of(task));
        when(taskRepository.findByAssignedEmployeeId(eq(1L), any(Pageable.class))).thenReturn(taskPage);

        // Fixed: Pass all 5 arguments (1L, 0, 10, "id", "asc")
        PagedResponseDTO<TaskResponseDTO> results = taskService.getTasksByEmployeeId(1L, 0, 10, "id", "asc");

        assertNotNull(results);
        assertFalse(results.content().isEmpty());
        assertEquals(1, results.content().size());
    }

    @Test
    void test14_UpdateTask_Success() {
        TaskRequestDTO updateDTO = new TaskRequestDTO(1L, "Updated Task", "Updated Desc", TaskStatus.IN_PROGRESS);
        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO result = taskService.updateTask(10L, updateDTO);

        assertNotNull(result);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void test15_DeleteTask_Success() {
        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(10L);

        verify(taskRepository, times(1)).delete(task);
    }
}
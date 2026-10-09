package employee.task.employee.service;

import employee.task.employee.domain.Employee;
import employee.task.employee.domain.Role;
import employee.task.employee.dtos.EmployeeRequestDTO;
import employee.task.employee.dtos.EmployeeResponseDTO;
import employee.task.employee.exception.ResourceNotFoundException;
import employee.task.employee.repository.EmployeeRepository;
import employee.task.employee.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .fullName("Jane Doe")
                .email("jane@example.com")
                .password("password123")
                .role(Role.ROLE_ADMIN)
                .build();

        requestDTO = new EmployeeRequestDTO("Jane Doe", "jane@example.com", "password123", Role.ROLE_ADMIN);
    }

    @Test
    void test1_CreateEmployee_Success() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDTO result = employeeService.createEmployee(requestDTO);

        assertNotNull(result);
        assertEquals("Jane Doe", result.fullName());
        assertEquals("jane@example.com", result.email());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void test2_GetEmployeeById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponseDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Jane Doe", result.fullName());
    }

    @Test
    void test3_GetEmployeeById_NotFound_ThrowsException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(99L));
    }

    @Test
    void test4_GetAllEmployees_Success() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeResponseDTO> results = employeeService.getAllEmployees();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void test5_UpdateEmployee_Success() {
        EmployeeRequestDTO updateDTO = new EmployeeRequestDTO("Jane Smith", "jane@example.com", "newpass", Role.ROLE_ADMIN);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDTO result = employeeService.updateEmployee(1L, updateDTO);

        assertNotNull(result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void test6_UpdateEmployee_NotFound_ThrowsException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(99L, requestDTO));
    }

    @Test
    void test7_DeleteEmployee_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).delete(employee);
    }
}
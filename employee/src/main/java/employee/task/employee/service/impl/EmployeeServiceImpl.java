package employee.task.employee.service.impl;

import employee.task.employee.domain.Employee;
import employee.task.employee.domain.Role;
import employee.task.employee.dtos.EmployeeRequestDTO;
import employee.task.employee.dtos.EmployeeResponseDTO;
import employee.task.employee.exception.BadRequestException;
import employee.task.employee.exception.ResourceNotFoundException;
import employee.task.employee.repository.EmployeeRepository;
import employee.task.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        if (employeeRepository.existsByEmail(requestDTO.email())) {
            throw new BadRequestException("Email already in use: " + requestDTO.email());
        }

        Role roleEnum = requestDTO.role() != null ? requestDTO.role() : Role.ROLE_EMPLOYEE;

        Employee employee = Employee.builder()
                .fullName(requestDTO.fullName())
                .email(requestDTO.email())
                .password(requestDTO.password())
                .role(roleEnum)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponseDTO(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToResponseDTO(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> responseDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            responseDTOs.add(mapToResponseDTO(employee));
        }

        return responseDTOs;
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setFullName(requestDTO.fullName());
        employee.setEmail(requestDTO.email());

        if (requestDTO.role() != null) {
            employee.setRole(requestDTO.role());
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToResponseDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

   private EmployeeResponseDTO mapToResponseDTO(Employee employee) {
    String roleStr = employee.getRole() != null ? employee.getRole().name() : null;

    return new EmployeeResponseDTO(
            employee.getId(),
            employee.getFullName(),
            employee.getEmail(),
            roleStr // Pass String instead of Role
    );
}
}
package employee.task.employee.service.impl;

import employee.task.employee.domain.Employee;
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
        if (employeeRepository.existsByEmail(requestDTO.getEmail())) {
            throw new BadRequestException("Email already in use: " + requestDTO.getEmail());
        }

        Employee employee = Employee.builder()
                .fullName(requestDTO.getFullName())
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .role(requestDTO.getRole())
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
            EmployeeResponseDTO dto = mapToResponseDTO(employee);
            responseDTOs.add(dto);
        }

        return responseDTOs;
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setFullName(requestDTO.getFullName());
        employee.setEmail(requestDTO.getEmail());
        employee.setRole(requestDTO.getRole());

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
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .role(employee.getRole())
                .build();
    }
}
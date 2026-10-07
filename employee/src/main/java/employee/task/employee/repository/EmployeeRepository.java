package employee.task.employee.repository;

import employee.task.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Spring parses this method name to automatically generate the SQL query
    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);
}
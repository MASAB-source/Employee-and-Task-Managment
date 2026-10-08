package employee.task.employee.security;

import employee.task.employee.domain.Employee;
import employee.task.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Convert domain Role to Spring Security GrantedAuthority format (e.g., "ROLE_EMPLOYEE")
        String roleName = employee.getRole() != null ? employee.getRole().name() : "EMPLOYEE";
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);

        // Return Spring Security's built-in User object
        return new User(
                employee.getEmail(),
                employee.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
package employee.task.employee.exception;

public class ResourceNotFoundException extends RuntimeException {

    // This constructor MUST be explicitly defined
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
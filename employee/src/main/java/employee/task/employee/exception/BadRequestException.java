package employee.task.employee.exception;

public class BadRequestException extends RuntimeException {

    // This constructor MUST be explicitly defined
    public BadRequestException(String message) {
        super(message); // Passes the error string to RuntimeException
    }
}
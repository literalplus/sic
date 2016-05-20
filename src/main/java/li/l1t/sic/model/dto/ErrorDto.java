package li.l1t.sic.model.dto;

import li.l1t.sic.exception.JsonPropagatingException;

/**
 * Data Transfer Object for soft errors.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
public class ErrorDto {
    private final String message;
    private final String exceptionName;

    public ErrorDto(String message) {
        this(message, null);
    }

    public ErrorDto(Exception exception) {
        this.message = exception.getMessage();
        if(exception instanceof JsonPropagatingException && exception.getCause() != null) {
            this.exceptionName = exception.getCause().getClass().getSimpleName();
        } else {
            this.exceptionName = exception.getClass().getSimpleName();
        }
    }

    public ErrorDto(String message, String exceptionName) {
        this.message = message;
        this.exceptionName = exceptionName;
    }

    public String getMessage() {
        return message;
    }

    public String getExceptionName() {
        return exceptionName;
    }
}

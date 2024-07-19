package app.ecosynergy.api.exceptions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

}

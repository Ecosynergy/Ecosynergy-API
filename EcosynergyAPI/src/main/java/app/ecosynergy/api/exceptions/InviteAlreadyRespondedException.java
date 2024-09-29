package app.ecosynergy.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InviteAlreadyRespondedException extends RuntimeException{
    public InviteAlreadyRespondedException(String message) {
        super(message);
    }
}

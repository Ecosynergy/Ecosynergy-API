package app.ecosynergy.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SelfAdditionToTeamException extends RuntimeException{

    public SelfAdditionToTeamException() {
        super("You cannot add yourself to the team");
    }

    public SelfAdditionToTeamException(String message) {
        super(message);
    }
}

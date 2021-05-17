package empik.user.rate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    String notFoundHandler(UserNotFound exception) {
        return "User with provided login does not exist";
    }

    @ExceptionHandler(GithubException.class)
    @ResponseStatus(code = HttpStatus.BAD_GATEWAY)
    @ResponseBody
    String serverNotAvailableHandler(GithubException exception) {
        return "Github server is unavailable";
    }
}

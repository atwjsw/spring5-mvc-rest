package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.ErrorDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
//public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFoundException(Exception exception, WebRequest request) {
        log.debug("handling not found exception", exception.getMessage());
        return new ErrorDTO("entity.not.found", exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadInputRecipeId(Exception ex, WebRequest request) {
        log.debug("handling number format exception", ex.getMessage());
        return new ErrorDTO("badinput.numberformat", ex.getMessage());
    }
}

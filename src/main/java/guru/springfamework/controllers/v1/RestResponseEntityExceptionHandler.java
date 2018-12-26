package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.ErrorDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(Exception exception, WebRequest request) {
        log.debug("handling not found exception", exception.getMessage());
        return new ResponseEntity<>(
                new ErrorDTO("entity.not.found", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleBadInputRecipeId(Exception ex) {
        log.debug("handling number format exception", ex.getMessage());
        return new ResponseEntity<>(new ErrorDTO("badinput.numberformat", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.ErrorDTO;
import guru.springfamework.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(Exception ex) {
        log.debug("handling not found exception", ex.getMessage());
        return new ResponseEntity<>(new ErrorDTO("entity.not.found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleBadInputRecipeId(Exception ex) {
        log.debug("handling number format exception", ex.getMessage());
        return new ResponseEntity<>(new ErrorDTO("badinput.numberformat", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

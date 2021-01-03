package br.com.bancoxyz.error;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.bancoxyz.error.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e,
            HttpServletRequest request) {
        String error = "Não foi encontrado nenhum registro no sistema!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> duplicateDataConstraint(DataIntegrityViolationException e,
            HttpServletRequest request) {
        String error = getField(e.getMessage()) + " já existe nos registros do sistema!";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getCause().getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    public String getField(String error) {
        if (error.indexOf("EMAIL") >= 0) {
            return "O E-mail";
        }
        return "O CPF";
    }
}

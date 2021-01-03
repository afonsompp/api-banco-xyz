package br.com.bancoxyz.error;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {
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

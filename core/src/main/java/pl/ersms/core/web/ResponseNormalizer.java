package pl.ersms.core.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ResponseNormalizer {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> handleConstraintViolationException(@NonNull final HttpServletRequest request,
                                                                   @NonNull final ConstraintViolationException ex) {
        log.debug("Normalizing exception {} for URI {}", ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<?> handleAccessDeniedException(@NonNull final HttpServletRequest request,
                                                            @NonNull final AccessDeniedException ex) {
        log.debug("Normalizing exception {} for URI {}", ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}

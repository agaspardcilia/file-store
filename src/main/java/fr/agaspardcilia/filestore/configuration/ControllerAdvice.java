package fr.agaspardcilia.filestore.configuration;

import fr.agaspardcilia.filestore.common.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.Instant;

/**
 * Application controller advice. Will intercept exception and convert them to HTTP error responses.
 */
@RestControllerAdvice
public class ControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvice.class);

    /**
     * Handle API exceptions.
     *
     * @param exception the exception.
     * @return the error.
     */
    @ExceptionHandler
    public ResponseEntity<ApiExceptionResponse> handleApiException(ApiException exception) {
        LOGGER.debug("{}: {}", exception.getStatus(), exception.getMessage());

        return new ResponseEntity<>(
                new ApiExceptionResponse(exception.getStatus(), exception.getMessage()), exception.getStatus()
        );
    }

    @ExceptionHandler
    public ResponseEntity<ApiExceptionResponse> handleMissingPartException(MissingServletRequestPartException exception) {
        LOGGER.debug("Multipart exception", exception);

        return new ResponseEntity<>(
                new ApiExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage()), HttpStatus.BAD_REQUEST
        );
    }

    public record ApiExceptionResponse(
            String httpStatus,
            int httpStatusCode,
            String message,
            Instant timestamp
    ) {
        public ApiExceptionResponse(HttpStatus httpStatus, String message) {
            this(httpStatus.name(), httpStatus.value(), message, Instant.now());
        }
    }
}

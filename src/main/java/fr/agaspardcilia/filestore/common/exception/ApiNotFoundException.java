package fr.agaspardcilia.filestore.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Use this when a {@link HttpStatus#NOT_FOUND} should be sent.
 */
public class ApiNotFoundException extends ApiException {
    public ApiNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

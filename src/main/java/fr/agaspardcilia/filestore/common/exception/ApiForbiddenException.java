package fr.agaspardcilia.filestore.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Use this when a {@link HttpStatus#FORBIDDEN} should be sent.
 */
public class ApiForbiddenException extends ApiException {
    public ApiForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

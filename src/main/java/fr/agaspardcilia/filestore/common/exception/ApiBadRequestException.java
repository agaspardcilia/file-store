package fr.agaspardcilia.filestore.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Use this when a {@link HttpStatus#BAD_REQUEST} should be sent.
 */
public class ApiBadRequestException extends ApiException {
    public ApiBadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

package fr.agaspardcilia.filestore.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Use this when a {@link HttpStatus#INTERNAL_SERVER_ERROR} should be sent.
 */
public class ApiInternalServerErrorException extends ApiException {
    public ApiInternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}

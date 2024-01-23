package fr.agaspardcilia.filestore.store;

/**
 * {@link StoreService} related exception.
 */
public class StoreException extends Exception {

    /**
     * Constructor.
     *
     * @param message the message of the exception.
     * @param cause the cause of the exception.
     */
    public StoreException(String message, Throwable cause) {
        super(message, cause);
    }
}

package fr.agaspardcilia.filestore.filemanager;

/**
 * {@link DirectoryManager} related exception.
 */
public class DirectoryManagerException extends Exception {
    /**
     * Constructor.
     *
     * @param message the message of the exception.
     */
    DirectoryManagerException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the message of the exception.
     * @param cause the cause of the exception.
     */
    DirectoryManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package fr.agaspardcilia.filestore.filemanager;

import java.io.File;
import java.nio.file.Path;

/**
 * General validation for {@link Path}s.
 */
class PathValidator {
    private final File file;

    /**
     * Constructor.
     *
     * @param path the {@link Path} that should be validated.
     */
    PathValidator(Path path) {
        this.file = path.toFile();
    }

    /**
     * Verifies that the {@link Path} exists.
     *
     * @return this for chaining.
     * @throws DirectoryManagerException if the condition was not satisfied.
     */
    PathValidator checkExists() throws DirectoryManagerException {
        if (!file.exists()) {
            throw new DirectoryManagerException("File does not exist");
        }
        return this;
    }

    /**
     * Verifies that the {@link Path} is a directory.
     *
     * @return this for chaining.
     * @throws DirectoryManagerException if the condition was not satisfied.
     */
    PathValidator checkIsDirectory() throws DirectoryManagerException {
        if (!file.isDirectory()) {
            throw new DirectoryManagerException("File is not a directory");
        }
        return this;
    }

    /**
     * Verifies that the {@link Path} is a file.
     *
     * @return this for chaining.
     * @throws DirectoryManagerException if the condition was not satisfied.
     */
    PathValidator checkIsFile() throws DirectoryManagerException {
        if (!file.isFile()) {
            throw new DirectoryManagerException("File is not a file");
        }
        return this;
    }

    /**
     * Verifies that the {@link Path} has read access.
     *
     * @return this for chaining.
     * @throws DirectoryManagerException if the condition was not satisfied.
     */
    PathValidator checkCanRead() throws DirectoryManagerException {
        if (!file.canWrite()) {
            throw new DirectoryManagerException("File does not have read permission");
        }
        return this;
    }

    /**
     * Verifies that the {@link Path} has write access.
     *
     * @return this for chaining.
     * @throws DirectoryManagerException if the condition was not satisfied.
     */
    PathValidator checkCanWrite() throws DirectoryManagerException {
        if (!file.canWrite()) {
            throw new DirectoryManagerException("File does not have write permission");
        }
        return this;
    }
}

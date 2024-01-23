package fr.agaspardcilia.filestore.filemanager;

import fr.agaspardcilia.filestore.util.NameUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Manages the content of a directory. This class is only meant to read/write files.
 */
public class DirectoryManager {
    private final Path directory;
    private final long sizeLimit;

    /**
     * Constructor. Note that this assumes that the directory exists and has read/write access.
     *
     * @param directory the {@link Path} to the managed directory.
     * @param sizeLimit in bytes.
     * @throws DirectoryManagerException if the directory does not exist or does not allow read and write.
     */
    public DirectoryManager(Path directory, long sizeLimit) throws DirectoryManagerException {
        this.directory = directory;
        this.sizeLimit = sizeLimit;
        verifyDirectory();
    }

    /**
     * Writes a file in the managed directory. If a file already exists with the same name, it will be replaced.
     *
     * @param fileData the file as a {@link byte[]}.
     * @param name the name of the file.
     * @return the created file.
     * @throws DirectoryManagerException if something goes wrong when writing the file. This also include lack of space available.
     */
    public File write(byte[] fileData, String name) throws DirectoryManagerException {
        try {
            if (!NameUtil.isFileNameValid(name)) {
                throw new DirectoryManagerException("Name is not valid");
            }

            if (fileData.length + getCurrentSize() > sizeLimit) {
                throw new DirectoryManagerException("Not enough available space to write this file");
            }

            Path path = directory.resolve(name);
            Files.write(path, fileData);
            return path.toFile();
        } catch (IOException e) {
            throw new DirectoryManagerException("Something went wrong during write operation", e);
        }
    }

    /**
     * Gets a file from the directory. This assumes that the file exists.
     *
     * @param name the name of the file.
     * @return the file.
     * @throws DirectoryManagerException if the file does not exist or is not a valid file.
     */
    public File getFile(String name) throws DirectoryManagerException {
        Path path = directory.resolve(name);
        PathValidator validator = new PathValidator(path);
        validator.checkExists()
                .checkIsFile()
                .checkCanRead();

        return path.toFile();
    }

    /**
     * Checks if a file exist in the directory for a given name.
     *
     * @param name the name of the file.
     * @return {@code true} if it exists, {@code false} otherwise.
     */
    public boolean exists(String name) {
        PathValidator validator = new PathValidator(directory.resolve(name));
        try {
            validator.checkExists();
        } catch (DirectoryManagerException e) {
            return false;
        }
        return true;
    }

    /**
     * @return the list of all the file in the directory. Entries that are not files will be ignored.
     */
    public List<File> listFiles() {
        File[] files = directory.toFile().listFiles();
        List<File> result = files != null ? List.of(files) : List.of();

        return result.stream()
                .filter(File::isFile)
                .toList();
    }

    // Returns the current size of the directory.
    private long getCurrentSize() {
        return listFiles().stream()
                .mapToLong(File::length)
                .sum();
    }

    private void verifyDirectory() throws DirectoryManagerException {
        PathValidator validator = new PathValidator(directory);
        validator.checkExists()
                .checkIsDirectory()
                .checkCanRead()
                .checkCanWrite();
    }
}

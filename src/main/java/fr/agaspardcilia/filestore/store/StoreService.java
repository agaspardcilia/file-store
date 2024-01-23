package fr.agaspardcilia.filestore.store;

import fr.agaspardcilia.filestore.configuration.properties.AppProperties;
import fr.agaspardcilia.filestore.configuration.properties.Store;
import fr.agaspardcilia.filestore.filemanager.DirectoryManager;
import fr.agaspardcilia.filestore.filemanager.DirectoryManagerException;
import fr.agaspardcilia.filestore.store.dto.FileResponse;
import fr.agaspardcilia.filestore.store.dto.FileResponseMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * A service that contains all the operations related to file storage.
 */
@Service
public class StoreService {
    private final DirectoryManager directoryManager;

    /**
     * Constructor.
     *
     * @param properties the {@link AppProperties}.
     * @throws DirectoryManagerException if the given properties does not produce a valid directory description.
     */
    public StoreService(AppProperties properties) throws DirectoryManagerException {
        Store store = properties.getStore();
        this.directoryManager = new DirectoryManager(store.getDirectory(), store.getSizeLimit());
    }

    /**
     * Stores a file.
     *
     * @param file the file to store as a {@link MultipartFile}.
     * @return the description of the written file as a {@link FileResponse}.
     * @throws StoreException if something goes wrong when writing the file.
     */
    public FileResponse storeFile(MultipartFile file) throws StoreException {
        try {
            File result = directoryManager.write(file.getBytes(), file.getOriginalFilename());
            return FileResponseMapper.toResponse(result);
        } catch (DirectoryManagerException e) {
            throw new StoreException(e.getMessage(), e);
        } catch (IOException e) {
            throw new StoreException("Write failed", e);
        }
    }

    /**
     * Gets a file for a given name.
     *
     * @param name the name of the file.
     * @return a {@link File} if one could be found, {@link Optional#empty()} otherwise.
     * @throws StoreException if something goes wrong when getting the file.
     */
    public Optional<File> getFile(String name) throws StoreException {
        if (!directoryManager.exists(name)) {
            return Optional.empty();
        }

        try {
            return Optional.of(directoryManager.getFile(name));
        } catch (DirectoryManagerException e) {
            throw new StoreException("Read failed", e);
        }
    }

    /**
     * @return a {@link List} of every available files as {@link FileResponse}s.
     */
    public List<FileResponse> listFiles() {
        return directoryManager.listFiles().stream()
                .map(FileResponseMapper::toResponse)
                .toList();
    }

}
